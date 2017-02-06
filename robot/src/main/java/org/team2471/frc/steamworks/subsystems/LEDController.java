package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.SerialPort;

public class LEDController {
  public static final String LOAD_FUEL = "LoadFuel";
  public static final String LOAD_GEAR = "LoadGear";

  private final SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kUSB1);

  public void sentLn(String message) {
    serialPort.writeString(message + '\n');
  }
}
