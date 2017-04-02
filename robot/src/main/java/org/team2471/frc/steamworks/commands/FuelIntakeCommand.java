package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class FuelIntakeCommand extends Command {
  private Timer timer = new Timer();

  public FuelIntakeCommand() {
    requires(Robot.fuelIntake);
  }

  @Override
  protected void initialize() {
    timer.start();
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
  protected void end() {
    Robot.fuelIntake.stopRoll();
    timer.stop();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
