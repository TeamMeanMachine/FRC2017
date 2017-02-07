package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

public class HardwareMap {
  public static final class TwinShooterMap {
    public static final CANTalon masterLeft = new CANTalon(0);
    public static final CANTalon slaveLeft = new CANTalon(0);
    public static final CANTalon masterRight = new CANTalon(0);
    public static final CANTalon slaveRight = new CANTalon(0);
    public static final CANTalon ballFeeder = new CANTalon(0);
  }
}
