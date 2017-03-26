package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class ExtendFuelFlapCommand extends Command {

  public ExtendFuelFlapCommand() {
    requires(Robot.flap);
  }

  @Override
  protected void execute() {
    Robot.flap.extend();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.flap.retract();
  }
}
