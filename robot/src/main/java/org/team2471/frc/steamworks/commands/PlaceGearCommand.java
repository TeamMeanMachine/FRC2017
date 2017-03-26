package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearCommand extends Command{

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    Robot.gearIntake.rollOut(0.4);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
  }
}
