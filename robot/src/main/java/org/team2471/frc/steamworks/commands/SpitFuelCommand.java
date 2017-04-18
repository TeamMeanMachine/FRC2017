package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class SpitFuelCommand extends Command {
  public SpitFuelCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    Robot.fuelIntake.rollOut();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}
