package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class ActiveGearGroundIntakeCommand extends Command{

  public ActiveGearGroundIntakeCommand() {
    requires(Robot.activeGearIntake);
    return;
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.activeGearIntake.extend();
    Robot.activeGearIntake.rollIn();
  }

  @Override
  protected boolean isFinished() {
    return Robot.activeGearIntake.hasGear();
  }

  @Override
  protected void end() {
    Robot.activeGearIntake.retract();
    Robot.activeGearIntake.rollStop();
  }
}
