package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.util.control.PDPDrawSensor;

public class FuelIntake extends Subsystem {
  public static final int INTAKE_CURRENT_LIMIT = 30;

  private CANTalon intakeMotor = HardwareMap.FuelIntakeMap.intakeMotor;
  private CANTalon leftWindshieldMotor = HardwareMap.FuelIntakeMap.leftWindshieldMotor;
  private CANTalon rightWindshieldMotor = HardwareMap.FuelIntakeMap.rightWindshieldMotor;
  private Solenoid intakeSolenoid = HardwareMap.FuelIntakeMap.intakeSolenoid;
  private PDPDrawSensor intakeDrawSensor = HardwareMap.FuelIntakeMap.intakeMotorDrawSensor;


  public FuelIntake() {
    intakeMotor.setInverted(true);

    leftWindshieldMotor.setInverted(true);
  }

  /**
   * Push intake out
   **/
  public void extend() {
    intakeSolenoid.set(true);
  }

  /**
   * Pull intake in
   **/
  public void retract() {
    intakeSolenoid.set(false);
  }

  /**
   * Run teh poly cords in
   * -> <-
   **/
  public void windshieldsIn() {
    leftWindshieldMotor.set(0.8);
    rightWindshieldMotor.set(0.8);
  }

  /**
   * Run intake motors in
   **/
  public void rollIn() {
    if(intakeDrawSensor.getCurrent() < INTAKE_CURRENT_LIMIT) {
      intakeMotor.set(0.8);
    } else {
      intakeMotor.set(0);
    }
  }

  public void windShieldsStop() {
    leftWindshieldMotor.set(0);
    rightWindshieldMotor.set(0);
  }

  public void stopRoll() {
    intakeMotor.set(0);
  }

  public void rollOut() {
    intakeMotor.set(-0.8);
  }


  @Override
  protected void initDefaultCommand() {
  }

}


