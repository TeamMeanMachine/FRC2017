package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.HardwareMap;

public class HopperWalls extends Subsystem {
  private final Solenoid solenoid = HardwareMap.HopperWallMap.solenoid;

  public void extend() {
    solenoid.set(true);
  }

  public void retract() {
    solenoid.set(false);
  }

  @Override
  protected void initDefaultCommand() {
  }
}
