package org.team2471.frc.steamworks.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.util.WaitingOnHardware;

public class GearIntake extends Subsystem {

  private final Solenoid tiltSolenoid = HardwareMap.GearIntakeMap.tiltSolenoid;
  private final Solenoid flapSolenoid = HardwareMap.GearIntakeMap.flapSolenoid;
  private final AnalogInput gearSensor = HardwareMap.GearIntakeMap.gearSensor;

  public GearIntake() {
    LiveWindow.addActuator("GearIntake", "Tilt Solenoid", tiltSolenoid);
    LiveWindow.addActuator("GearIntake", "Flap Solenoid", flapSolenoid);
    LiveWindow.addSensor("GearIntake", "Gear Sensor", gearSensor);
  }

  /**
   * TiltGearIntakeCommand gear intake forward
   */
  public void extend() {
    tiltSolenoid.set(true);
  }

  /**
   * TiltGearIntakeCommand back gear intake
   */
  public void retract() {
    tiltSolenoid.set(false);
  }

  /**
   * Put flaps forward
   */
  public void openFlaps() {
    flapSolenoid.set(true);
  }

  /**
   * Put flaps back
   */
  public void closeFlaps() {
    flapSolenoid.set(false);
  }

  /**
   * Has the gear
   */
  @WaitingOnHardware
  public boolean hasGear() {
    return gearSensor.getValue() < 1500;
  }

  @Override
  protected void initDefaultCommand() {

  }
}
