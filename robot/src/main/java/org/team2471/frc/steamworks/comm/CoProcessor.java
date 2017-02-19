package org.team2471.frc.steamworks.comm;

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

  private class ServerRunnable implements Runnable {
    @Override
    public void run() {
      DatagramSocket serverSocket;
      try {
        serverSocket = new DatagramSocket(5800);
        byte[] receiveData = new byte[1024];
        //noinspection InfiniteLoopStatement
        while (true) {
          DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(packet);
          String message = new String(packet.getData());

          try {
            String[] splitMessage = message.split(";");
            String context = splitMessage[0];

            Consumer<VisionData> updater = data -> {
              logger.warn("Context " + context + " does not exist.");
            };
            if(context.equals("BOILER")) {
              updater = CoProcessor.this::setBoilerData;
            }

            if(splitMessage[1].equals("NONE")) {
              updater.accept(VisionData.empty());
            } else  {
              double error = Double.parseDouble(splitMessage[1]);
              double distance = Double.parseDouble(splitMessage[2]);
              double latency = Double.parseDouble(splitMessage[3]);
              updater.accept(VisionData.from(error, distance, latency));
            }
          } catch (RuntimeException ignored) {
            logger.warn("Invalid packet received: " + message);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
