package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.subsystems.GearIntake;

public class GearSpitCommand extends Command {

  public GearSpitCommand(){
    requires(Robot.gearIntake);
  }

  @Override
  protected void initialize() {
    Robot.gearIntake.openFlaps();
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return Robot.gearIntake.hasGear();
  }

  @Override
  protected void end() {
    Robot.gearIntake.closeFlaps();
  }

  @Override
  protected void interrupted() {

  }
}
