package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class RunFlashlightCommand extends Command {
  @Override
  protected void execute() {
    Robot.shooter.enableFlashlight();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.shooter.disableFlashlight();
  }
}
