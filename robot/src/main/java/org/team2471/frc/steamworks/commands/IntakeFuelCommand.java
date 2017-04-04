package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class IntakeFuelCommand extends Command {
  public IntakeFuelCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    Robot.fuelIntake.rollIn();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}
