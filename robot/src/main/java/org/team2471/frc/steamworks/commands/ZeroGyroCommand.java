package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team2471.frc.steamworks.HardwareMap;

public class ZeroGyroCommand extends InstantCommand {
  @Override
  protected void initialize() {
    HardwareMap.gyro.reset();
  }
}
