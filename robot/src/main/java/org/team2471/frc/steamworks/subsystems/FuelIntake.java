package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FuelIntake extends Subsystem {

  public CANTalon intakeMotor = HardwareMap.FuelIntakeMap.intakeMotor;
  public CANTalon leftWindshieldMotor = HardwareMap.FuelIntakeMap.leftWindshieldMotor;
  public CANTalon rightWindshieldMotor = HardwareMap.FuelIntakeMap.rightWindshieldMotor;
  public Solenoid intakeSolenoid = HardwareMap.FuelIntakeMap.intakeSolenoid;


  public FuelIntake() {
  }

  /** Push intake out
     **/
    public void extend() {
      intakeSolenoid.set(true);
    }
    /** Pull intake in
     **/
    public void retract() {
      intakeSolenoid.set(false);
    }
  /** Run teh poly cords in
   * -> <-
   **/
    public void windshieldsIn() {
      leftWindshieldMotor.set(0.8);
      rightWindshieldMotor.set(0.8);
    }

    /** Run intake motors in
     **/
    public void rollIn() {
      intakeMotor.set(0.8);
    }


  @Override
  protected void initDefaultCommand() {
  }

  }


