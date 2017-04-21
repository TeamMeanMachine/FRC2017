package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class DriveDefaultCommand extends Command {

  public DriveDefaultCommand() {
    requires(Robot.drive);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.drive.disableClimbing();
    double throttle = IOMap.throttleAxis.get();
    Robot.drive.drive(throttle, IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get());
    SmartDashboard.putNumber("Drive Speed", Robot.drive.getSpeed());

    double leftDistance = Robot.drive.getLeftMotor1().getPosition();
    double rightDistance = Robot.drive.getRightMotor1().getPosition();
    SmartDashboard.putString("Drive Distances", leftDistance + ":" + rightDistance);

    // temporary test for shooter
/*
    HardwareMap.TwinShooterMap.masterLeft.changeControlMode( CANTalon.TalonControlMode.PercentVbus );
    HardwareMap.TwinShooterMap.masterRight.changeControlMode( CANTalon.TalonControlMode.PercentVbus );

    double shooterPower = IOMap.coDriverThrottleAxis.get();
    HardwareMap.TwinShooterMap.masterLeft.set( shooterPower );
    HardwareMap.TwinShooterMap.masterRight.set( shooterPower );

    SmartDashboard.putNumber("Shooter Right Speed", Robot.shooter.getRightSpeed());
    SmartDashboard.putNumber("Shooter Left Speed", Robot.shooter.getLeftSpeed());
*/
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
