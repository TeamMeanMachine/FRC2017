package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class EnableRingLightCommand extends Command {
  @Override
  protected void execute() {
    Robot.shooter.enableRingLight(0.2);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.shooter.disableRingLight();
  }
}
