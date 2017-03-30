package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.team2471.frc.lib.io.log.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class UPBoard extends Subsystem {
  private final NetworkTable networkTable = NetworkTable.getTable("Vision");
  private final Logger logger = new Logger("UPBoard");

  private final Timer receiveTimer = new Timer();

  private State state = State.IDLE;

  private boolean dataPresent = false;
  private OptionalDouble error = OptionalDouble.empty();
  private OptionalDouble distance = OptionalDouble.empty();
  private OptionalDouble timestamp = OptionalDouble.empty();

  public UPBoard() {
    networkTable.putString("Mode", "IDLE");
    new Thread(this::runThread).start(); // start thread
    receiveTimer.start();
  }

  public boolean isDataPresent() {
    return dataPresent;
  }

  public OptionalDouble getError() {
    return error;
  }

  public OptionalDouble getDistance() {
    return distance;
  }

  public OptionalDouble getTimestamp() {
    return timestamp;
  }

  public void setState(State state) {
    this.state = state;
    networkTable.putString("Mode", state.name());
  }

  @Override
  protected void initDefaultCommand() {
  }

  public enum State {
    IDLE,
    BOILER,
  }

  public boolean isConnected() {
    return receiveTimer.get() < 2;
//    return false;
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
        receiveTimer.reset();
        String message = new String(packet.getData());
        logger.trace("Packet received: " + message);

        if(message.startsWith("NONE")) {
          dataPresent = false;
          error = OptionalDouble.empty();
          error = OptionalDouble.empty();
          distance = OptionalDouble.empty();
        }

        try {
          String[] splitMessage = message.split(";");

          double timestamp = Integer.parseInt(splitMessage[0]);
          double error = Double.parseDouble(splitMessage[1]);
          double distance = Double.parseDouble(splitMessage[2]);

          dataPresent = true;
          this.timestamp = OptionalDouble.of(timestamp);
          this.error = OptionalDouble.of(error);
          this.distance = OptionalDouble.of(distance);
        } catch (RuntimeException ignored) {
          //logger.warn("Invalid packet received: " + message);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
