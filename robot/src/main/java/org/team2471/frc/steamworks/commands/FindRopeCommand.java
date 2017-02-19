package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.lib.io.log.Logger;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;


public class FindRopeCommand extends Command {
  public static final int CURRENT_THRESHOLD = 45;
  private final Logger logger = new Logger("FindRopeCommand");

  public FindRopeCommand() {
    requires(Robot.drive);
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    Robot.drive.driveStraight(IOMap.playAnimationAxis.get() - IOMap.reverseAnimationAxis.get(), false);
    logger.debug("Current: " + Robot.fuelIntake.getCurrent());
    Robot.fuelIntake.rollIn();
  }

  @Override
  protected boolean isFinished() {
    return Robot.fuelIntake.getCurrent() > CURRENT_THRESHOLD;
  }

  @Override
  protected void end() {
    Robot.fuelIntake.stopRoll();
    Robot.drive.driveStraight(0, false);
  }
}
