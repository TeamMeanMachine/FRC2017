package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import static org.team2471.frc.steamworks.HardwareMap.FuelIntakeMap.flapSolenoid;

public class FuelFlap extends Subsystem {
  public void extend() {
    flapSolenoid.set(true);
  }

  public void retract() {
    flapSolenoid.set(false);
  }

  @Override
  protected void initDefaultCommand() {

  }
}
