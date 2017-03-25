package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.Robot;

public class UpdateRPMCommand extends InstantCommand {
  private final double step;

  public UpdateRPMCommand(double step) {
    this.step = step;
  }

  @Override
  protected void initialize() {
    if (SmartDashboard.getBoolean("Auto Aim", false)) {
      SmartDashboard.putNumber("Shooter Offset", SmartDashboard.getNumber("Shooter Offset", 0) + step);
    }
    else if (Robot.shooter.getRPMPreset()>= 0) {
      String name = "RPM" + Robot.shooter.getRPMPreset();
      SmartDashboard.putNumber(name, SmartDashboard.getNumber(name, 2500) + step);
    }
  }
}
