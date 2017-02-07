package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDController extends Subsystem {
  public static final String LOAD_FUEL = "LoadFuel";
  public static final String LOAD_GEAR = "LoadGear";

  private final SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kUSB1);

  public void sendLn(String message) {
    serialPort.writeString(message + '\n');
  }

  @Override
  protected void initDefaultCommand() {

  }
}
