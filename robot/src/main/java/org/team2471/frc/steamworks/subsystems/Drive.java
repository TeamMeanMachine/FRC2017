package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.team254.frc2016.CheesyDriveHelper;
import com.team254.lib.util.DriveSignal;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.control.CANController;
import org.team2471.frc.lib.io.log.Logger;
import org.team2471.frc.lib.motion_profiling.util.TankDriveProfile;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.defaultcommands.DriveDefaultCommand;

public class Drive extends Subsystem {
  public static final double HIGH_SHIFTPOINT = 6.0;
  public static final double LOW_SHIFTPOINT = 2.0;
  public static final int CODES_PER_REV = 216;
  public static final double EDGES_PER_100_MS = CODES_PER_REV * 4.0 / 10.0;
  private final CANController leftMotor1 = HardwareMap.DriveMap.leftMotor1;
  private final CANTalon leftMotor2 = HardwareMap.DriveMap.leftMotor2;
  private final CANTalon leftMotor3 = HardwareMap.DriveMap.leftMotor3;
  private final CANController rightMotor1 = HardwareMap.DriveMap.rightMotor1;
  private final CANTalon rightMotor2 = HardwareMap.DriveMap.rightMotor2;
  private final CANTalon rightMotor3 = HardwareMap.DriveMap.rightMotor3;
  private final Solenoid shiftPTO = HardwareMap.DriveMap.shiftPTO;
  private final Solenoid climbPTO = HardwareMap.DriveMap.climbPTO;
  private final Logger logger = new Logger("Drive");
  private final SendableChooser<ShiftState> shiftStateChooser = new SendableChooser<>();
  private CheesyDriveHelper cheesyDriveHelper;

  public final TankDriveProfile tankDriveProfile = new TankDriveProfile(leftMotor1, rightMotor1);

  public Drive() {
    leftMotor1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    leftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    leftMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftMotor3.changeControlMode(CANTalon.TalonControlMode.Follower);

    // Only invert this motor on competition bot
    leftMotor3.setInverted(true);
    leftMotor3.reverseOutput(true);

    leftMotor2.set(leftMotor1.getDeviceID());
    leftMotor3.set(leftMotor1.getDeviceID());

    rightMotor1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    rightMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    rightMotor1.setInverted(true);
    rightMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightMotor3.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightMotor2.set(rightMotor1.getDeviceID());
    rightMotor3.set(rightMotor1.getDeviceID());

    leftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    leftMotor1.reverseSensor(true);
    leftMotor1.reverseOutput(false);
    leftMotor1.configEncoderCodesPerRev(CODES_PER_REV);
    leftMotor1.setProfile(0);
    leftMotor1.setF(0);
    leftMotor1.setPID(2.0, 0, 2.0);

    rightMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    rightMotor1.reverseSensor(false);
    rightMotor1.reverseOutput(true);
    rightMotor1.configEncoderCodesPerRev(CODES_PER_REV);
    rightMotor1.setProfile(0);
    rightMotor1.setF(0);
    rightMotor1.setPID(2.0, 0, 2.0);

    cheesyDriveHelper = new CheesyDriveHelper();

    // limit voltage ramp rate
    leftMotor1.setVoltageRampRate(9);
    rightMotor1.setVoltageRampRate(9);

    // ramp rates
    leftMotor1.setVoltageRampRate(72);
    leftMotor2.setVoltageRampRate(72);
    leftMotor3.setVoltageRampRate(72);
    rightMotor1.setVoltageRampRate(72);
    rightMotor2.setVoltageRampRate(72);
    rightMotor3.setVoltageRampRate(72);

    shiftStateChooser.addDefault("Automatic", ShiftState.AUTO);
    shiftStateChooser.addObject("Force High", ShiftState.FORCE_HIGH);
    shiftStateChooser.addObject("Force Low", ShiftState.FORCE_LOW);

    SmartDashboard.putData("Drive Shifting", shiftStateChooser);

    LiveWindow.addActuator("Drive", "Right Motor 1", rightMotor1);
    LiveWindow.addActuator("Drive", "Right Motor 2", rightMotor2);
    LiveWindow.addActuator("Drive", "Right Motor 3", rightMotor3);
    LiveWindow.addActuator("Drive", "Left Motor 1", leftMotor1);
    LiveWindow.addActuator("Drive", "Left Motor 2", leftMotor2);
    LiveWindow.addActuator("Drive", "Left Motor 3", leftMotor3);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveDefaultCommand());
  }

  public void drive(double throttle, double turn, double turnLeft, double turnRight, ShiftState shiftState) {
    // WE CANNOT TURN WHILE PTO IS ENGAGED. Use driveStraight() while climbing.
    if (isClimbing()) {
      logger.error("Robot attempted to use drive() while climber is engaged!");
      return;
    }

    DriveSignal signal = cheesyDriveHelper.cheesyDrive(throttle, turn, false);

    if(shiftState == ShiftState.AUTO) {
      double averageSpeed = getSpeed();
      if (averageSpeed > HIGH_SHIFTPOINT) {
        hiGear();
      } else if (averageSpeed < LOW_SHIFTPOINT) {
        lowGear();
      }
      SmartDashboard.putNumber("Speed", averageSpeed);
    } else if(shiftState == ShiftState.FORCE_HIGH) {
      hiGear();
    } else {
      lowGear();
    }

    double leftPower = signal.leftMotor - turnLeft + turnRight;
    double rightPower = signal.rightMotor - turnRight + turnLeft;

    double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    if (maxPower > 1) {
      leftPower /= maxPower;
      rightPower /= maxPower;
    }

    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);

    //leftMotor1.setStatusFrameRateMs();

    SmartDashboard.putNumber("Left Distance", -leftMotor1.getPosition());
    SmartDashboard.putNumber("Right Distance", -rightMotor1.getPosition());
    SmartDashboard.putNumber("Distance", getDistance());
  }


  public void drive(double throttle, double turn, double turnLeft, double turnRight) {
    drive(throttle, turn, turnLeft, turnRight, ShiftState.AUTO);
  }
  public void turnInPlace(double turn) {
    drive(0, 0, 0, turn);
  }

  public void driveStraight(double throttle, boolean highGear) {
    if (highGear) {
      hiGear();
    } else {
      lowGear();
    }
    leftMotor1.set(throttle);
    rightMotor1.set(throttle);
  }

  public void arcade(double throttle, double turn) {
    drive(throttle, 0, 0, turn);
  }

  public void enableClimbing() {
    climbPTO.set(false);
  }

  public void disableClimbing() {
    climbPTO.set(true);
  }

  public boolean isClimbing() {
    return !climbPTO.get();
  }

  public double getSpeed() {  // Math.abs() only at the end, so that turning in place doesn't shift to high gear
    SmartDashboard.putNumber("Left Speed", -leftMotor1.getEncVelocity() / EDGES_PER_100_MS);  // getSpeed() is faster - unsure about units.
    SmartDashboard.putNumber("Right Speed", rightMotor1.getEncVelocity() / EDGES_PER_100_MS);
    return Math.abs(-leftMotor1.getEncVelocity() / EDGES_PER_100_MS + rightMotor1.getEncVelocity() / EDGES_PER_100_MS) / 2.0;
  }

  public double getDistance() {
    SmartDashboard.putNumber("Left Distance", leftMotor1.getPosition());
    SmartDashboard.putNumber("Right Distance", rightMotor1.getPosition());
    return (Math.abs(leftMotor1.getPosition()) + Math.abs(rightMotor1.getPosition())) / 2;
  }

  public void setPID(double p, double i, double d) {
    leftMotor1.setPID(p, i, d);
    rightMotor1.setPID(p, i, d);
  }

  public CANController getLeftMotor1() {
    return leftMotor1;
  }

  public CANController getRightMotor1() {
    return rightMotor1;
  }

  public double getAngle() {
    return (leftMotor1.getPosition() - rightMotor1.getPosition()) / (32.0 / 12.0 * Math.PI) * 158.0;
  }

  private void shift(boolean state) {
    ShiftState shiftState = shiftStateChooser.getSelected();
    if (shiftState == ShiftState.AUTO) {
      shiftPTO.set(state); // should do this 99% of the time
    } else if (shiftState == ShiftState.FORCE_HIGH) {
      shiftPTO.set(false);
    } else {
      shiftPTO.set(true);
    }
  }

  public void lowGear() {
    shift(true);
  }

  public void hiGear() {
    shift(false);
  }

  public enum ShiftState {
    AUTO,
    FORCE_HIGH,
    FORCE_LOW
  }
}
