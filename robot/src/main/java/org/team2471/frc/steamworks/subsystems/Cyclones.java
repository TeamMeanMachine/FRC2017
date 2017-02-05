package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import static org.team2471.frc.steamworks.HardwareMap.CycloneMap.cyclonesMotor;

public class Cyclones extends Subsystem {


  @Override
  protected void initDefaultCommand() {

  }
  public void spin(){
    cyclonesMotor.set(1);
  }
  public void stop(){
    cyclonesMotor.set(0);
  }
}
