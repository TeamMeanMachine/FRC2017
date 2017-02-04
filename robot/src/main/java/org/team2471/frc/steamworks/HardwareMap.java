package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

public class HardwareMap {
  public static final class Drive {
    public static final CANTalon rightMotor1 = new CANTalon(0);
    public static final CANTalon rightMotor2 = new CANTalon(0);
    public static final CANTalon rightMotor3 = new CANTalon(0);
    public static final CANTalon leftMotor1 = new CANTalon(0);
    public static final CANTalon leftMotor2 = new CANTalon(0);
    public static final CANTalon leftMotor3 = new CANTalon(0);
  }
}
