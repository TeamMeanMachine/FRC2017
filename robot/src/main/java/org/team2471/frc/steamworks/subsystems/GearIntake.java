package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.HardwareMap;

public class GearIntake extends Subsystem {

  private Solenoid leftFlap = HardwareMap.GearIntake.leftFlap;
  private Solenoid rightFlap = HardwareMap.GearIntake.rightFlap;
  private Solenoid leftTilt = HardwareMap.GearIntake.leftTilt;
  private Solenoid rightTilt = HardwareMap.GearIntake.rightTilt;
  private DigitalInput gearSensor = HardwareMap.GearIntake.gearSensor;

  /**
   * Tilt gear intake forward
   */

  public void tiltForward(){
    leftTilt.set(true);
    rightTilt.set(true);
  }

  /**
   * Tilt back gear intake
   */

  public void tiltBack(){
    leftTilt.set(false);
    rightTilt.set(false);
  }

  /**
   * Put flaps forward
   */

  public void openFlaps(){
    leftFlap.set(true);
    rightFlap.set(true);
  }

  /**
   * Put flaps back
   */

  public void closeFlaps() {
    leftFlap.set(false);
    rightFlap.set(false);
  }

  /**
   * Has the gear
   */

  public boolean hasGear(){
    return gearSensor.get();
  }

  @Override
  protected void initDefaultCommand() {

  }
}
