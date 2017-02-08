package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

public class IntakeGearCommand extends Command {

  public IntakeGearCommand() {
    requires (Robot.gearIntake);
  }

  @Override
  protected void initialize() {
    Robot.gearIntake.closeFlaps();
    Robot.gearIntake.tiltForward();
  }


  @Override
  protected void end() {
    Robot.gearIntake.tiltBack();
  }


  @Override
  protected boolean isFinished() {
    return Robot.gearIntake.hasGear();
  }
}
