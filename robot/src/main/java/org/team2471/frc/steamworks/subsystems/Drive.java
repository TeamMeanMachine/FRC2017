package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.team254.frc2016.CheesyDriveHelper;
import com.team254.lib.util.DriveSignal;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.defaultcommands.DriveDefaultCommand;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.*;

public class Drive extends Subsystem {

  private CheesyDriveHelper cheesyDriveHelper;
  public static final double HIGH_SHIFTPOINT = 5.0;
  public static final double LOW_SHIFTPOINT = 2.0;


  public Drive(){
    leftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    leftMotor1.setInverted(true);
    leftMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftMotor3.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftMotor2.set(leftMotor1.getDeviceID());
    leftMotor3.set(leftMotor1.getDeviceID());

    rightMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    rightMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightMotor3.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightMotor2.set(rightMotor1.getDeviceID());
    rightMotor3.set(rightMotor1.getDeviceID());

    leftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    leftMotor1.reverseSensor(false);
    leftMotor1.reverseOutput(false);
    leftMotor1.configEncoderCodesPerRev(820 / 4);
    leftMotor1.setProfile(0);
    leftMotor1.setF(0);
    leftMotor1.setProfile(0);
    leftMotor1.setF(0);
    leftMotor1.setPID(2.0, 0, 2.0);

    rightMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    rightMotor1.reverseSensor(true);
    rightMotor1.reverseOutput(true);
    rightMotor1.configEncoderCodesPerRev(820 / 4);
    rightMotor1.setProfile(0);
    rightMotor1.setF(0);
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
  }

  public double getSpeed() {
    return (Math.abs(leftMotor1.getEncVelocity()) + Math.abs(rightMotor1.getEncVelocity())) / 2.0;
  }

  public void setPID(double p,double i,double d){
    leftMotor1.setPID(p, i, d);
    rightMotor1.setPID(p, i, d);
  }
}
