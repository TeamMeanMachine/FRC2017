package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Environment;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class FuelIntakeDefaultCommand extends Command {
  public FuelIntakeDefaultCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {

    if(!Robot.drive.isClimbing() &&
        Environment.FMS ? IOMap.throttleAxis.get() >= 0 : IOMap.throttleAxis.get() > 0) {
      Robot.fuelIntake.rollIn();
    } else {
      Robot.fuelIntake.stopRoll();
    }
  }

  @Override
  protected void end() {
    Robot.fuelIntake.stopRoll();
  }

  @Override
  protected boolean isFinished() {
    return Robot.drive.isClimbing() || isTimedOut();
  }
}
