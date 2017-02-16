package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class AutoAimCommand extends PIDCommand {

  private final PIDController pidController = getPIDController();

  public AutoAimCommand() {
    super(0, 0, 0);
    requires(Robot.drive);
    requires(Robot.twinShooter);
  }

  @Override
  protected void initialize() {
    Robot.twinShooter.enable();
    pidController.enable();
  }


  @Override
  protected void end() {
    Robot.twinShooter.disable();
    pidController.disable();
  }

  @Override
  public void execute() {
    if (IOMap.shootButton.get()) {
      Robot.twinShooter.enableFeed();
    }
    else {
      Robot.twinShooter.disableFeed();
    }
  }



  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected double returnPIDInput() {
    return Robot.coProcessor.getShooterTargetError();
  }

  @Override
  protected void usePIDOutput(double output) {

    Robot.drive.drive(0,output,0,0);
  }
}