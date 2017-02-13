package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

public class HardwareMap {
  public static final class TwinShooterMap {
    public static final CANTalon masterLeft = new CANTalon(4);
    public static final CANTalon slaveLeft = new CANTalon(5);
    public static final CANTalon masterRight = new CANTalon(11);
    public static final CANTalon slaveRight = new CANTalon(10);
    public static final CANTalon ballFeeder = new CANTalon(6);
  }

  public static final class CycloneMap{
    public static final CANTalon cyclonesMotor = new CANTalon(12);
  }
}
