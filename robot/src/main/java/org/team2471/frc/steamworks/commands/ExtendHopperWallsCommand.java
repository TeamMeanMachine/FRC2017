package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class ExtendHopperWallsCommand extends Command {
  private final Timer timer = new Timer();

  public ExtendHopperWallsCommand() {
    requires(Robot.walls);
  }

  @Override
  protected void initialize() {
    timer.start();
    Robot.walls.extend();
  }

  @Override
  protected boolean isFinished() {
//    return timer.get() > 0.35 || isTimedOut();
    return isTimedOut();
  }

  @Override
  protected void end() {
    timer.stop();
    Robot.walls.retract();
  }
}
