package org.team2471.frc.util;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class FakeGyro implements Gyro {
  @Override
  public void calibrate() {

  }

  @Override
  public void reset() {

  }

  @Override
  public double getAngle() {
    return 0;
  }

  @Override
  public double getRate() {
    return 0;
  }

  @Override
  public void free() {

  }
}
