package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class DriveDefaultCommand extends Command {
  public DriveDefaultCommand() {
    requires(Robot.drive);
  }

  @Override
  protected void execute() {
    Robot.drive.drive(IOMap.throttleAxis.get(), IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get());
    SmartDashboard.putNumber("Drive Distance", Robot.drive.getDistance());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
