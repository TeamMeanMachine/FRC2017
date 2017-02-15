package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class FeedGearCommand extends Command {
  public FeedGearCommand() {
    requires(Robot.gearIntake);
  }

  @Override
  protected void execute() {
    Robot.gearIntake.openFlaps();
    Robot.gearIntake.retract();
  }

  @Override
  protected void end() {
    Robot.gearIntake.closeFlaps();
  }


  @Override
  protected boolean isFinished() {
    return false;
  }
}

