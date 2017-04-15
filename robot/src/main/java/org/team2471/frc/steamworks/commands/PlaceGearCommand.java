package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class PlaceGearCommand extends Command {
  private final Timer timer = new Timer();

  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    if(timer.get() < 1.5) {
      Robot.gearIntake.rollOut(0.6);
    } else {
      Robot.gearIntake.rollIn();
      IOMap.getDriverController().rumbleLeft(0.1f);
      IOMap.getDriverController().rumbleRight(0.1f);
    }
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
    IOMap.getDriverController().rumbleLeft(0.0f);
    IOMap.getDriverController().rumbleRight(0.0f);
    timer.stop();
  }
}
