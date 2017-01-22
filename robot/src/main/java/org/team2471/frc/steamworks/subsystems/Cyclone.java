package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Cyclone extends Subsystem {
  private CANTalon spinMotor = new CANTalon(0);

  public Cyclone() {
  }

  @Override
  protected void initDefaultCommand() {

  }

  public void spin() {
    spinMotor.set(1);
  }

  public void stop() {
    spinMotor.set(0);
  }

}