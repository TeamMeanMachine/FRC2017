package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearCommand extends Command{

  private Timer activeGearTimer = new Timer();
  @Override
  protected void initialize() {
    activeGearTimer.start();
  }

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    if(activeGearTimer.get() > .25) {
      Robot.gearIntake.rollOut();
      activeGearTimer.reset();
    }
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    activeGearTimer.stop();
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
  }
}
