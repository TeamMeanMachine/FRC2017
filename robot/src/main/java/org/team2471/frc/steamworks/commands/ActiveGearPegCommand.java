package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ActiveGearPegCommand extends Command{

  private Timer activeGearTimer = new Timer();
  @Override
  protected void initialize() {
    activeGearTimer.start();
  }

  @Override
  protected void execute() {
    Robot.activeGearIntake.extend();
    if(activeGearTimer.get() > .25) {
      Robot.activeGearIntake.rollOut();
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
    Robot.activeGearIntake.retract();
    Robot.activeGearIntake.rollStop();
  }
}
