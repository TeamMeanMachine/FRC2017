package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import org.team2471.frc.lib.control.CANController;
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
    public static final CANController rightMotor1 = new CANController(15);
    public static final CANTalon rightMotor2 = new CANTalon(14);
    public static final CANTalon rightMotor3 = new CANTalon(13);

    public static final CANController leftMotor1 = new CANController(0);
    public static final CANTalon leftMotor2 = new CANTalon(1);
    public static final CANTalon leftMotor3 = new CANTalon(2);

    public static final Solenoid climbPTO = new Solenoid(0);
    public static final Solenoid shiftPTO = new Solenoid(5);

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
    public static final AnalogInput gearSensor = new AnalogInput(3);
  }
}
