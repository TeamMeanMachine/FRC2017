package org.team2471.frc.steamworks.comm;

public class VisionData {
  private final double error;
  private final double distance;
  private final int imageNumber;
  private final boolean present;

  private VisionData(int imageNumber, double error, double distance, boolean present) {
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

  public int getImageNumber() {
    return imageNumber;
  }

  static VisionData from(int imageNumber, double error, double distance) {
    return new VisionData(imageNumber, error, distance, true);
  }

  static VisionData empty() {
    return new VisionData(0, Double.NaN, Double.NaN, false);
  }

  @Override
  public String toString() {
    return present ? "Image: " + imageNumber + "\nError: " + error + "\nDistance: " + distance
        : "Image: " + imageNumber + "\nEMPTY";
  }
}
