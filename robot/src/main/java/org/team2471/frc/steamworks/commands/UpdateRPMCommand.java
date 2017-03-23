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
    if (Robot.shooter.isHoodUp() && Robot.coProcessor.getBoilerData().targetPresent()) {
      SmartDashboard.putNumber("Shooter Offset", SmartDashboard.getNumber("Shooter Offset", 0) + step);
    }
    else {
      SmartDashboard.putNumber("Shooter Setpoint", SmartDashboard.getNumber("Shooter Setpoint", 2500) + step);
    }
  }
}
