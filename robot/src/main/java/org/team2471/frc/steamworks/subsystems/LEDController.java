package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.lib.io.log.Logger;

public class LEDController extends Subsystem {
  private final Logger logger = new Logger("LEDController");
  private SerialPort serialPort;

  public LEDController() {
    new Thread(() -> {
      while(serialPort == null) {
        try {
          serialPort = new SerialPort(9600, SerialPort.Port.kUSB1);
        } catch(RuntimeException ignored) {
          try {
            logger.warn("Could not establish connection with teensy. Retrying...", 10);
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      logger.info("Teensy connected.");
    }).start();
  }

  public void write(String message) {
    if(serialPort != null) {
      serialPort.writeString(message + '\n');
    }
  }

  @Override
  protected void initDefaultCommand() {
  }
}
