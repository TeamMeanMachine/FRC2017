package org.team2471.frc.steamworks.comm;

public class VisionData {
  private final double error;
  private final double distance;
  private final double imageNumber;
  private final boolean present;

  private VisionData(double error, double distance, double imageNumber, boolean present) {
    this.error = error;
    this.imageNumber = imageNumber;
    this.distance = distance;
    this.present = present;
  }

  public boolean targetPresent() {
    return present;
  }

  public double getError() {
    return error;
  }

  public double getDistance() {
    return distance;
  }

  public double getImageNumber() {
    return imageNumber;
  }

  static VisionData from(double error, double distance, double latency) {
    return new VisionData(error, distance, latency, true);
  }

  static VisionData empty() {
    return new VisionData(Double.NaN, Double.NaN, Double.NaN, false);
  }
}
