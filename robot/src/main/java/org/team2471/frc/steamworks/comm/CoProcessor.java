package org.team2471.frc.steamworks.comm;

import edu.wpi.first.wpilibj.Timer;
import org.team2471.frc.lib.io.log.LogLevel;
import org.team2471.frc.lib.io.log.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.function.Consumer;

public class CoProcessor {
  private final Logger logger = new Logger("CoProcessor");
  private VisionData boilerData = VisionData.empty();
  private VisionData gearData = VisionData.empty();
  private VisionData ropeData = VisionData.empty();

  private double lastTime = Timer.getFPGATimestamp();

  public CoProcessor() {
    logger.setLocalLogLevel(LogLevel.INFO);
    logger.trace("Coprocessor initialized");
    new Thread(this::runThread).start(); // start thread
  }

  public VisionData getBoilerData() {
    return boilerData;
  }

  private void setBoilerData(VisionData boilerData) {
    this.boilerData = boilerData;
  }

  public VisionData getGearData() {
  return gearData;
  }

  private void setGearData(VisionData gearData) {
    this.gearData = gearData;
  }

  public VisionData getRopeData() {
    return ropeData;
  }

  private void setRopeData(VisionData ropeData) {
    this.ropeData = ropeData;
  }

  private void runThread() {
    try {
      DatagramSocket serverSocket = new DatagramSocket(5800);
      byte[] receiveData = new byte[1024];
      //noinspection InfiniteLoopStatement
      while (true) {
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        logger.trace("Waiting for packet...");
        serverSocket.receive(packet);
        String message = new String(packet.getData());
        logger.trace("Packet received: " + message);

        if(message.startsWith("NONE")) {
          setBoilerData(VisionData.empty());
          setGearData(VisionData.empty());
          setRopeData(VisionData.empty());
          continue;
        }

        try {
          String[] splitMessage = message.split(";");
          String context = splitMessage[0];

          Consumer<VisionData> updater = data -> {
            logger.warn("Context " + context + " does not exist.");
          };

          switch (context) {
            case "BOILER":
              updater = this::setBoilerData;
              break;
            case "GEAR":
              updater = this::setGearData;
              break;
            case "ROPE":
              updater = this::setRopeData;
              break;
          }

          int imageNumber = Integer.parseInt(splitMessage[1]);
          String errorString = splitMessage[2];
          double error = Double.parseDouble(errorString);
          double distance = Double.parseDouble(splitMessage[3]);
          VisionData data = VisionData.from(imageNumber, error, distance);
          updater.accept(data);
          logger.trace(data.toString());
        } catch (RuntimeException ignored) {
          logger.warn("Invalid packet received: " + message);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
