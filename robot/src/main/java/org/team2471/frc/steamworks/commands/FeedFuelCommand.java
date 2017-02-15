package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class FeedFuelCommand extends Command {
  public FeedFuelCommand() {
    requires(Robot.gearIntake);
  }

  @Override
  protected void execute() {
    Robot.gearIntake.closeFlaps();
    Robot.gearIntake.extend();
  }

  @Override
  protected void end() {
    Robot.gearIntake.closeFlaps();
    Robot.gearIntake.retract();
  }


  @Override
  protected boolean isFinished() {
    return false;
  }
}
