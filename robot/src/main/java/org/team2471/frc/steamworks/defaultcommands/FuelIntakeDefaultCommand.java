package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class FuelIntakeDefaultCommand extends Command {
  public FuelIntakeDefaultCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    if (IOMap.spitButton.get() && !Robot.drive.isClimbing()) {
      Robot.fuelIntake.rollOut();
    } else if (IOMap.useIntakeButton.get() && !Robot.drive.isClimbing()) {
      Robot.fuelIntake.rollIn();
    } else {
      Robot.fuelIntake.stopRoll();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
