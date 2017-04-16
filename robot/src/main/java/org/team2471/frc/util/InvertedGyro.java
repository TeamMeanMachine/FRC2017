package org.team2471.frc.util;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class InvertedGyro implements Gyro {
  private final Gyro gyro;

  public InvertedGyro(Gyro gyro) {
    this.gyro = gyro;
  }

  @Override
  public void calibrate() {
    gyro.calibrate();
  }

  @Override
  public void reset() {
    gyro.reset();
  }

  @Override
  public double getAngle() {
    return -gyro.getAngle();
  }

  @Override
  public double getRate() {
    return -gyro.getRate();
  }

  @Override
  public void free() {
    gyro.free();
  }
}
