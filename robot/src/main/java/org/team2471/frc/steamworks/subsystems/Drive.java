package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.team254.frc2016.CheesyDriveHelper;
import com.team254.lib.util.DriveSignal;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.control.CANController;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.defaultcommands.DriveDefaultCommand;

public class Drive extends Subsystem {
  private final CANController leftMotor1 = HardwareMap.DriveMap.leftMotor1;
  private final CANTalon leftMotor2 = HardwareMap.DriveMap.leftMotor2;
  private final CANTalon leftMotor3 = HardwareMap.DriveMap.leftMotor3;

  private final CANController rightMotor1 = HardwareMap.DriveMap.rightMotor1;
  private final CANTalon rightMotor2 = HardwareMap.DriveMap.rightMotor2;
  private final CANTalon rightMotor3 = HardwareMap.DriveMap.rightMotor3;

  private final Solenoid shiftSolenoid = HardwareMap.DriveMap.shiftSolenoid;

  private CheesyDriveHelper cheesyDriveHelper;
  public static final double HIGH_SHIFTPOINT = 5.0;
  public static final double LOW_SHIFTPOINT = 2.0;
  public static final int CODES_PER_REV = 216;
  public static final double EDGES_PER_100_MS = CODES_PER_REV * 4.0 / 10.0;


  public Drive() {
    leftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    leftMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftMotor3.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftMotor2.set(leftMotor1.getDeviceID());
    leftMotor3.set(leftMotor1.getDeviceID());

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
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveDefaultCommand());
  }

  public void drive(double throttle, double turn, double turnLeft, double turnRight) {
    DriveSignal signal = cheesyDriveHelper.cheesyDrive(throttle, turn, false);
    leftMotor1.set(signal.leftMotor - turnLeft);
    rightMotor1.set(signal.rightMotor + turnRight);

    double averageSpeed = getSpeed();
    if (averageSpeed > HIGH_SHIFTPOINT) {
      shiftSolenoid.set(false);  // high gear
    } else if (averageSpeed < LOW_SHIFTPOINT) {
      shiftSolenoid.set(true);
    }

    SmartDashboard.putNumber("Speed", averageSpeed);
    double leftPower = signal.leftMotor - turnLeft + turnRight;
    double rightPower = signal.rightMotor - turnRight + turnLeft;

    double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    if(maxPower > 1) {
      leftPower /= maxPower;
      rightPower /= maxPower;
    }

    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);
  }

  public double getSpeed() {
    return Math.abs(-leftMotor1.getEncVelocity() / EDGES_PER_100_MS + rightMotor1.getEncVelocity() / EDGES_PER_100_MS) / 2.0;
  }

  public double getDistance() {
    return (Math.abs(leftMotor1.getPosition()) + Math.abs(rightMotor1.getPosition())) / 2;
  }

  public void setPID(double p,double i,double d){
    leftMotor1.setPID(p, i, d);
    rightMotor1.setPID(p, i, d);
  }
}
