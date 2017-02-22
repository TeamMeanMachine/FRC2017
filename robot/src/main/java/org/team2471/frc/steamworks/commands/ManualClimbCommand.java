package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class ManualClimbCommand extends Command {
  public ManualClimbCommand() {
    requires(Robot.drive);
    requires(Robot.fuelIntake);
  }

  @Override
  protected void initialize() {
    Robot.drive.enableClimbing();
    Robot.fuelIntake.extend();
  }

  @Override
  protected void execute() {
    Robot.drive.driveStraight(-IOMap.playAnimationAxis.get() + IOMap.reverseAnimationAxis.get(), false);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }
}
