package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.subsystems.Drive;


public class FindRopeCommand extends Command {
  public static final int CURRENT_THRESHOLD = 50;

  private final ControllerAxis throttleAxis = IOMap.throttleAxis;
//      .map(value -> value > 0.35 ? 0.35 : value);

  public FindRopeCommand() {
    requires(Robot.drive);
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    Robot.drive.drive(throttleAxis.get(), IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get(),
        Drive.ShiftState.FORCE_LOW);
    Robot.fuelIntake.rollOut();
  }

  @Override
  protected boolean isFinished() {
    return Robot.fuelIntake.getCurrent() > CURRENT_THRESHOLD || IOMap.exitClimbButton.get();
  }

  @Override
  protected void end() {
    Robot.fuelIntake.stopRoll();
    Robot.drive.driveStraight(0, false);
  }
}
