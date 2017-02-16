package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import org.team2471.frc.lib.control.MeanMotorController;
import org.team2471.frc.util.control.PDPDrawSensor;

public class HardwareMap {
  public static final PowerDistributionPanel pdp = new PowerDistributionPanel();

  public static final class TwinShooterMap {
    public static final CANTalon masterLeft = new CANTalon(4);
    public static final CANTalon slaveLeft = new CANTalon(5);
    public static final CANTalon masterRight = new CANTalon(11);
    public static final CANTalon slaveRight = new CANTalon(10);
    public static final CANTalon ballFeeder = new CANTalon(6);
    public static final CANTalon cycloneMotor = new CANTalon(12);
    public static final Solenoid hoodSolenoid = new Solenoid(4);
  }

  public static final class DriveMap {
    public static final MeanMotorController rightMotor1 = new MeanMotorController(14);
    public static final CANTalon rightMotor2 = new CANTalon(15);
    public static final CANTalon rightMotor3 = new CANTalon(13);

    public static final MeanMotorController leftMotor1 = new MeanMotorController(1);
    public static final CANTalon leftMotor2 = new CANTalon(0);
    public static final CANTalon leftMotor3 = new CANTalon(2);

    public static final Solenoid climbPTO = new Solenoid(0);
    public static final Solenoid shiftPTO = new Solenoid(5);
    public static final Solenoid shiftSolenoid = new Solenoid(5);

  }

  public static final class FuelIntakeMap {
    public static final CANTalon intakeMotor = new CANTalon(3);
    public static final PDPDrawSensor intakeMotorDrawSensor = () -> pdp.getCurrent(3);
    public static final CANTalon leftWindshieldMotor = new CANTalon(9);
    public static final CANTalon rightWindshieldMotor = new CANTalon(8);
    public static final Solenoid intakeSolenoid = new Solenoid(1);
  }

  public static final class GearIntakeMap {
    public static final Solenoid flapSolenoid = new Solenoid(3);
    public static final Solenoid tiltSolenoid = new Solenoid(2);
    public static final DigitalInput gearSensor = new DigitalInput(0);
  }
}
