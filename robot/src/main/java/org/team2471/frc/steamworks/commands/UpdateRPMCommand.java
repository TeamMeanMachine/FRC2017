package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UpdateRPMCommand extends InstantCommand {
  private final double step;

  public UpdateRPMCommand(double step) {
    this.step = step;
  }

  @Override
  protected void initialize() {
    SmartDashboard.putNumber("Shooter Setpoint", SmartDashboard.getNumber("Shooter Setpoint", 6000) + step);
  }
}
