package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

public class HardwareMap {
  public static final class Drive {
    public static final CANTalon rightMotor1 = new CANTalon(0);
    public static final CANTalon rightMotor2 = new CANTalon(1);
    public static final CANTalon rightMotor3 = new CANTalon(2);
    public static final CANTalon leftMotor1 = new CANTalon(3);
    public static final CANTalon leftMotor2 = new CANTalon(4);
    public static final CANTalon leftMotor3 = new CANTalon(5);
  }
  public static final class TwinShooter{
    public static final CANTalon leftShooterMotor1 = new CANTalon(6);
    public static final CANTalon leftShooterMotor2 = new CANTalon(7);
    public static final CANTalon rightShooterMotor1 = new CANTalon(8);
    public static final CANTalon rightShooterMotor2 = new CANTalon(9);
  }
}
