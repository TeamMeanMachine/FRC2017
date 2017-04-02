package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class CenterGearCommand extends Command {

  public CenterGearCommand() {
    requires(Robot.gearIntake);
  }

  @Override
  protected void execute() {
    Robot.gearIntake.rollIn(0.8);
  }

  @Override
  protected void end() {
    Robot.gearIntake.rollStop();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}
