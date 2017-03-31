package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.log.Logger;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;


public class FindRopeCommand extends Command {
  public static final int CURRENT_THRESHOLD = 50;
  private final Logger logger = new Logger("FindRopeCommand");

  public FindRopeCommand() {
    requires(Robot.drive);
    requires(Robot.fuelIntake);
  }

  @Override
  protected void execute() {
    Robot.drive.drive(IOMap.throttleAxis.get(), IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get());
    SmartDashboard.putNumber("Climber Current", Robot.fuelIntake.getCurrent());
    Robot.fuelIntake.rollOut();
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
