package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class DisableFuelIntakeCommand extends Command {
  public DisableFuelIntakeCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.fuelIntake.stopRoll();
  }
}
