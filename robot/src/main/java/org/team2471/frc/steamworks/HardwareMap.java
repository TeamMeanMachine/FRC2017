package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;

public class HardwareMap {
  public static final class CycloneMap{
    public static final CANTalon cyclonesMotor = new CANTalon(12);
  public static final class Drive {
    public static final CANTalon rightMotor1 = new CANTalon(15);
    public static final CANTalon rightMotor2 = new CANTalon(14);
    public static final CANTalon rightMotor3 = new CANTalon(13);
    public static final CANTalon leftMotor1 = new CANTalon(0);
    public static final CANTalon leftMotor2 = new CANTalon(1);
    public static final CANTalon leftMotor3 = new CANTalon(2);
  }

  public static final class FuelIntakeMap {
    public static final CANTalon intakeMotor = new CANTalon(3);
    public static final CANTalon leftWindshieldMotor = new CANTalon(9);
    public static final CANTalon rightWindshieldMotor = new CANTalon(8);
    public static final Solenoid intakeSolenoid = new Solenoid(0);

  }
}
