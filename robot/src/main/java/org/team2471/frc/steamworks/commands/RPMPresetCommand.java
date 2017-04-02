package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.Robot;

public class RPMPresetCommand extends InstantCommand {
  private final int presetNumber;

  public RPMPresetCommand(int presetNumber) {
    this.presetNumber = presetNumber;
  }

  @Override
  protected void initialize() {
    String name = "RPM" + presetNumber;
    SmartDashboard.putNumber("Shooter Setpoint", SmartDashboard.getNumber(name, 2500));
    Robot.shooter.setRPMPreset(presetNumber);
    SmartDashboard.putBoolean("Auto Aim", false);
    if (presetNumber == 0) {
      Robot.shooter.retractHood();
    } else {
      Robot.shooter.extendHood();
    }
  }
}
