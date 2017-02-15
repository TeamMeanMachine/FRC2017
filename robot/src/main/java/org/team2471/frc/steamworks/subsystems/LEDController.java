package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDController extends Subsystem {
  private final SerialPort serialPort = new SerialPort(9600, SerialPort.Port.kOnboard);

  public void write(String message) {
    serialPort.writeString(message + '\n');
  }

  @Override
  protected void initDefaultCommand() {

  }
}
