package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class PickupGearCommand extends Command {
  private final Timer timer = new Timer();

  private final Timer ampTimer = new Timer();

  public PickupGearCommand() {
    requires(Robot.gearIntake);
  }

  @Override
  protected void initialize() {
    timer.start();
    ampTimer.start();
  }

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    if (timer.get() > 0.05) {
      Robot.gearIntake.rollIn();
    }

    double current = Robot.gearIntake.getCurrentDraw();
    if(current < 10.0) {
      ampTimer.reset();
    }
  }

  @Override
  protected boolean isFinished() {
    return Robot.gearIntake.hasGear() || ampTimer.get() > 0.25 || isTimedOut();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
    ampTimer.stop();
  }
}
