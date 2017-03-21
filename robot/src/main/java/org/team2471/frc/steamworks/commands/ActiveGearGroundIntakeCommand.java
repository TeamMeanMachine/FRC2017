package org.team2471.frc.steamworks.commands;

import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.subsystems.ActiveGearIntake;


import edu.wpi.first.wpilibj.Timer;
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
    Robot.activeGearIntake.down();
    Robot.activeGearIntake.gearIn();
  }

  @Override
  protected boolean isFinished() {
    return Robot.activeGearIntake.hasGear();
  }

  @Override
  protected void end() {
    Robot.activeGearIntake.up();
    Robot.activeGearIntake.motorStop();
  }
}
