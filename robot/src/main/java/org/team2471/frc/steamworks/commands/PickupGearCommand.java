package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class PickupGearCommand extends Command {
  private final Timer timer = new Timer();

  public PickupGearCommand() {
    requires(Robot.gearIntake);
  }

  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    if (timer.get() > 0.05) {
      Robot.gearIntake.rollIn();
    }
  }

  @Override
  protected boolean isFinished() {
    return Robot.gearIntake.hasGear() || isTimedOut();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
  }
}
