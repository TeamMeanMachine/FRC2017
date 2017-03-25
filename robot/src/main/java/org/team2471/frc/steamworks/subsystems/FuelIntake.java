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
  private PDPDrawSensor intakeDrawSensor = HardwareMap.FuelIntakeMap.intakeMotorDrawSensor;
  private Solenoid flapSolenoid1 = HardwareMap.FuelIntakeMap.flapSolenoid1;
  private Solenoid flapSolenoid2 = HardwareMap.FuelIntakeMap.flapSolenoid2;

  public FuelIntake() {
    intakeMotor.enableBrakeMode(false);
    intakeMotor.setInverted(true);

    amperageTimer.start();
    LiveWindow.addActuator("FuelIntake", "Intake Motor", intakeMotor);
    LiveWindow.addActuator("FuelIntake", "Left Windshield Motor", intakeMotor);
    LiveWindow.addActuator("FuelIntake", "Right Windshield Motor", intakeMotor);
  }

  public double getCurrent() {
    return intakeDrawSensor.getCurrent();
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

  public void stopRoll() {
    intakeMotor.set(0);
  }
  public void extendFlap() {
    flapSolenoid1.set(true);
    flapSolenoid2.set(true);
  }

  public void retractFlap(){
    flapSolenoid1.set(false);
    flapSolenoid2.set(false);
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


