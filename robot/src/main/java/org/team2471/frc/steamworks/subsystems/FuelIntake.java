package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.team2471.frc.lib.io.log.LogLevel;
import org.team2471.frc.lib.io.log.Logger;
import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.defaultcommands.FuelIntakeDefaultCommand;
import org.team2471.frc.util.control.PDPDrawSensor;

public class FuelIntake extends Subsystem {
  public static final int INTAKE_CURRENT_LIMIT = 60;

  private final Logger logger = new Logger("FuelIntake");
  private final Timer amperageTimer = new Timer();
  private static boolean overLimit = false;

  private CANTalon intakeMotor = HardwareMap.FuelIntakeMap.intakeMotor;
  private CANTalon leftWindshieldMotor = HardwareMap.FuelIntakeMap.leftWindshieldMotor;
  private CANTalon rightWindshieldMotor = HardwareMap.FuelIntakeMap.rightWindshieldMotor;
  private Solenoid intakeSolenoid = HardwareMap.FuelIntakeMap.intakeSolenoid;
  private PDPDrawSensor intakeDrawSensor = HardwareMap.FuelIntakeMap.intakeMotorDrawSensor;


  public FuelIntake() {
    intakeMotor.enableBrakeMode(false);
    intakeMotor.setInverted(true);
    leftWindshieldMotor.setInverted(false);
    rightWindshieldMotor.setInverted(true);

    amperageTimer.start();
    LiveWindow.addActuator("FuelIntake", "Intake Motor", intakeMotor);
    LiveWindow.addActuator("FuelIntake", "Left Windshield Motor", intakeMotor);
    LiveWindow.addActuator("FuelIntake", "Right Windshield Motor", intakeMotor);
    LiveWindow.addActuator("FuelIntake", "Solenoid", intakeSolenoid);
  }

  public double getCurrent() {
    return intakeDrawSensor.getCurrent();
  }

  public boolean isExtended() {
    return intakeSolenoid.get();
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

  public void toggle(){
    intakeSolenoid.set(!intakeSolenoid.get());
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
    if(current > INTAKE_CURRENT_LIMIT) {
      if (!overLimit) {
        overLimit = true;
        amperageTimer.reset();
        logger.warn("Current limit exceeded!", 1);
      }
    }
    else {
      overLimit = false;
    }

    boolean stalled = overLimit && amperageTimer.get() > 1.0;
    intakeMotor.set(stalled ? 0 : 0.8);
  }

  public void windShieldsStop() {
    leftWindshieldMotor.set(0);
    rightWindshieldMotor.set(0);
  }

  public void stopRoll() {
    intakeMotor.set(0);
  }

  public void rollOut() {
    double current = intakeDrawSensor.getCurrent();
    logger.debug("Current: " + current, 1);
    if(current > INTAKE_CURRENT_LIMIT) {
      if (!overLimit) {
        overLimit = true;
        amperageTimer.reset();
        logger.warn("Current limit exceeded!", 1);
      }
    }
    else {
      overLimit = false;
    }

    boolean stalled = overLimit && amperageTimer.get() > 1.0;
    intakeMotor.set(stalled ? 0 : -0.8);
  }


  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new FuelIntakeDefaultCommand());
  }

}


