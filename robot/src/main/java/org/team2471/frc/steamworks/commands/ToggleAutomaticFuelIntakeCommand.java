package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleAutomaticFuelIntakeCommand extends InstantCommand {

  @Override
  protected void initialize() {
    SmartDashboard.putBoolean("Automatic Intake", !SmartDashboard.getBoolean("Automatic Intake", true));
  }
}
