package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class ExtendHopperWallsCommand extends Command {
  public ExtendHopperWallsCommand() {
    requires(Robot.walls);
  }

  @Override
  protected void initialize() {
    Robot.walls.extend();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.walls.retract();
  }
}
