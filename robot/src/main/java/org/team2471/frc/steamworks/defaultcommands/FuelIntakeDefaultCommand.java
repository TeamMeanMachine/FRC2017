package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.team2471.frc.steamworks.Environment;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class FuelIntakeDefaultCommand extends Command {
  public FuelIntakeDefaultCommand() {
    requires(Robot.fuelIntake);
    SmartDashboard.putBoolean("Automatic Intake", true);
  }

  @Override
  protected void execute() {
    if(SmartDashboard.getBoolean("Automatic Intake", true) &&
        !Robot.drive.isClimbing() &&
        (Environment.FMS ? IOMap.throttleAxis.get() >= 0 : IOMap.throttleAxis.get() > 0)) {
      Robot.fuelIntake.rollIn();
    } else {
      Robot.fuelIntake.stopRoll();
    }
  }

  @Override
  protected void end() {
    Robot.fuelIntake.stopRoll();
  }

  @Override
  protected boolean isFinished() {
    return Robot.drive.isClimbing() || isTimedOut();
  }
}
