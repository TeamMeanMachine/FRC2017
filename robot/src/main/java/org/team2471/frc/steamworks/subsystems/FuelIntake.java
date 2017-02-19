package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.lib.io.log.LogLevel;
import org.team2471.frc.lib.io.log.Logger;
import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.defaultcommands.FuelIntakeDefaultCommand;
import org.team2471.frc.util.control.PDPDrawSensor;

public class FuelIntake extends Subsystem {
  public static final int INTAKE_CURRENT_LIMIT = 50;

  private final Logger logger = new Logger("FuelIntake");

  private CANTalon intakeMotor = HardwareMap.FuelIntakeMap.intakeMotor;
  private CANTalon leftWindshieldMotor = HardwareMap.FuelIntakeMap.leftWindshieldMotor;
  private CANTalon rightWindshieldMotor = HardwareMap.FuelIntakeMap.rightWindshieldMotor;
  private Solenoid intakeSolenoid = HardwareMap.FuelIntakeMap.intakeSolenoid;
  private PDPDrawSensor intakeDrawSensor = HardwareMap.FuelIntakeMap.intakeMotorDrawSensor;


  public FuelIntake() {
    intakeMotor.enableBrakeMode(false);
    intakeMotor.setInverted(true);
    leftWindshieldMotor.setInverted(true);
  }

  public double getCurrent() {
    return intakeDrawSensor.getCurrent();
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
    double current = intakeDrawSensor.getCurrent();
    logger.debug("Current: " + current, 1);
    if(current < INTAKE_CURRENT_LIMIT) {
      intakeMotor.set(0.8);
    } else {
      intakeMotor.set(0);
      logger.warn("Current limit exceeded!", 1);
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
    setDefaultCommand(new FuelIntakeDefaultCommand());
  }

}


