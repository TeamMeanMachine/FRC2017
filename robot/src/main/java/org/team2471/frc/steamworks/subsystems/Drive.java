package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.team254.frc2016.CheesyDriveHelper;
import com.team254.lib.util.DriveSignal;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.defaultcommands.DriveDefaultCommand;

import static org.team2471.frc.steamworks.HardwareMap.DriveTrainMap.*;

public class Drive extends Subsystem {

  private CheesyDriveHelper cheesyDriveHelper;

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
    leftMotor1.reverseSensor(false);
    leftMotor1.reverseOutput(false);
    leftMotor1.configEncoderCodesPerRev(0);
    leftMotor1.setProfile(0);
    leftMotor1.setF(0);
    leftMotor1.setPID(0, 0, 0);
    leftMotor1.setProfile(0);
    leftMotor1.setF(0);
    leftMotor1.setPID(0, 0, 0);

    rightMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    rightMotor1.reverseSensor(true);
    rightMotor1.reverseOutput(true);
    rightMotor1.configEncoderCodesPerRev(0);
    rightMotor1.setProfile(0);
    rightMotor1.setF(0);
    rightMotor1.setPID(0, 0, 0);
    rightMotor1.setProfile(0);
    rightMotor1.setF(0);
    rightMotor1.setPID(0, 0, 0);

    cheesyDriveHelper = new CheesyDriveHelper();
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveDefaultCommand());
  }

  public void drive(double throttle, double turn, double turnLeft, double turnRight) {
    DriveSignal signal = cheesyDriveHelper.cheesyDrive(throttle, turn, false);
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
    return (Math.abs(leftMotor1.getSpeed()) + Math.abs(rightMotor1.getSpeed())) / 2;
  }

  public void setPID(double p, double i, double d) {
    leftMotor1.setPID(p, i, d);
    rightMotor1.setPID(p, i, d);
  }
}
