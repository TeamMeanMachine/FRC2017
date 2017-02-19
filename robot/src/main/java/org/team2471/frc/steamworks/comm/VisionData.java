package org.team2471.frc.steamworks.comm;

public class VisionData {
  private final double error;
  private final double distance;
  private final double latency;
  private final boolean present;

  private VisionData(double error, double distance, double latency, boolean present) {
    this.error = error;
    this.latency = latency;
    this.distance = distance;
    this.present = present;
  }

  public boolean isPresent() {
    return present;
  }

  public double getError() {
    return error;
  }

  public double getDistance() {
    return distance;
  }

  public double getLatency() {
    return latency;
  }

  static VisionData from(double error, double distance, double latency) {
    return new VisionData(error, distance, latency, true);
  }

  static VisionData empty() {
    return new VisionData(Double.NaN, Double.NaN, Double.NaN, false);
  }
}
