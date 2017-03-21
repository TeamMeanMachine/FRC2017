package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ActiveGearIntake extends Subsystem{
  private final Solenoid gearSolenoid = HardwareMap.ActiveGearIntakeMap.gearSolenoid;
  private final CANTalon wheelMotor = HardwareMap.ActiveGearIntakeMap.wheelMotor;
  private final AnalogInput gearSensor = HardwareMap.ActiveGearIntakeMap.gearSensor;

  public ActiveGearIntake() {
  }

  /**Tilts the gear intake down.**/
  public void down() {
    gearSolenoid.set(true);
  }

  /**Tilts the gear intake up.**/
  public void up() {
    gearSolenoid.set(false);
  }

  /**Runs the wheely motor in.**/
  public void gearIn() {
    wheelMotor.set(.5);
  }

  /**Runs the wheely motor out.**/
  public void gearOut() {
    wheelMotor.set(-.5);
  }

  /**Stops wheely motors.**/
  public void motorStop() {
    wheelMotor.set(0);
  }

  /**Gear sensor thingymabobber**/
  public boolean hasGear() {
    return gearSensor.getValue() < 123456789;
  }
  @Override
  protected void initDefaultCommand() {

  }
}
