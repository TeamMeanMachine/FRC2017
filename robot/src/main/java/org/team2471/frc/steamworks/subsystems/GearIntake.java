package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearIntake extends Subsystem{
  private final Solenoid gearSolenoid = HardwareMap.GearIntakeMap.gearSolenoid;
  private final CANTalon wheelMotor = HardwareMap.GearIntakeMap.wheelMotor;
  private final AnalogInput gearSensor = HardwareMap.GearIntakeMap.gearSensor;

  /**Tilts the gear intake extend.**/
  public void extend() {
    gearSolenoid.set(true);
  }

  /**Tilts the gear intake retract.**/
  public void retract() {
    gearSolenoid.set(false);
  }

  public boolean isExtended() {
    return gearSolenoid.get();
  }

  /**Runs the wheely motor in.**/
  public void rollIn() {
    wheelMotor.set(.5);
  }

  /**Runs the wheely motor out.**/
  public void rollOut() {
    wheelMotor.set(-.5);
  }

  /**Stops wheely motors.**/
  public void rollStop() {
    wheelMotor.set(0);
  }

  /**Gear sensor thingymabobber**/
  public boolean hasGear() {
//    return gearSensor.getValue() < 123456789; // TODO: Find value
    return false;
  }
  @Override
  protected void initDefaultCommand() {

  }
}
