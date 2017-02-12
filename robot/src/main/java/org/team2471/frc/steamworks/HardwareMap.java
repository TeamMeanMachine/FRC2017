package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Created by lpct2 on 1/21/2017.
 */
public class HardwareMap {

  public static final class GearIntake {
    public static final Solenoid leftFlap = new Solenoid(1);
    public static final Solenoid rightFlap = new Solenoid(2);
    public static final Solenoid leftTilt = new Solenoid(3);
    public static final Solenoid rightTilt = new Solenoid(4);
    public static final DigitalInput gearSensor = new DigitalInput(0);
  }
}
