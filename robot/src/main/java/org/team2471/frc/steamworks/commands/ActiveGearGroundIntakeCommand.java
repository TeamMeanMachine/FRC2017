package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class ActiveGearGroundIntakeCommand extends Command{

  public ActiveGearGroundIntakeCommand() {
    requires(Robot.gearIntake);
    return;
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.gearIntake.extend();
    Robot.gearIntake.rollIn();
  }

  @Override
  protected boolean isFinished() {
    return Robot.gearIntake.hasGear();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
  }
}
