package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class AimAndShootCommand extends PIDCommand {

  private final PIDController pidController = getPIDController();

  public AimAndShootCommand() {
    super(0, 0, 0);
    requires(Robot.drive);
    requires(Robot.twinShooter);

    pidController.setAbsoluteTolerance(0.1);
    pidController.setToleranceBuffer(10);
  }

  @Override
  protected void initialize() {
    Robot.twinShooter.enable();
    pidController.enable();
  }


  @Override
  public void execute() {
    Robot.twinShooter.setRPM(SmartDashboard.getNumber("SHOOTER_SETPOINT", 0));

    // in rpm/s
    SmartDashboard.putNumber("SHOOTER_LEFT_SPEED", Robot.twinShooter.getLeftSpeed());
    SmartDashboard.putNumber("SHOOTER_LEFT_ERROR", Robot.twinShooter.getLeftError());

    SmartDashboard.putNumber("SHOOTER_RIGHT_SPEED", Robot.twinShooter.getRightSpeed());
    SmartDashboard.putNumber("SHOOTER_RIGHT_ERROR", Robot.twinShooter.getRightError());

    Robot.twinShooter.setPIDF(
        SmartDashboard.getNumber("SHOOTER_P", 0),
        SmartDashboard.getNumber("SHOOTER_I", 0),
        SmartDashboard.getNumber("SHOOTER_D", 0),
        SmartDashboard.getNumber("SHOOTER_F", 0)
    );

    SmartDashboard.putNumber("SHOOTER_LEFT_VOLTAGE", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("SHOOTER_RIGHT_VOLTAGE", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);


    boolean shoot = SmartDashboard.getBoolean("Auto Aim", false) ?
        pidController.onTarget() : IOMap.shootButton.get();

    if (shoot) {
      Robot.twinShooter.enableFeed();
    } else {
      Robot.twinShooter.disableFeed();
    }
  }



  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.twinShooter.disable();
    pidController.disable();
  }

  @Override
  protected double returnPIDInput() {
    return SmartDashboard.getBoolean("Auto Aim", false) ?
        Robot.coProcessor.getShooterTargetError() :
        IOMap.aimAxis.get() * 45;
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.turnInPlace(output);
  }
}