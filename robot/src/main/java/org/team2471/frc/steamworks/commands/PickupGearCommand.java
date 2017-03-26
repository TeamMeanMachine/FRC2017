package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class PickupGearCommand extends Command{

  public PickupGearCommand() {
    requires(Robot.gearIntake);
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
    return Robot.gearIntake.hasGear() || isTimedOut();
  }

  @Override
  protected void end() {
    Robot.gearIntake.retract();
    Robot.gearIntake.rollStop();
  }
}
