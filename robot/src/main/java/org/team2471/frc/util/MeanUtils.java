package org.team2471.frc.util;

public class MeanUtils {
  public static double range(double num, double min, double max) {
    if(num > max) {
      num = max;
    } else if(num < min) {
      num = min;
    }
    return num;
  }
}
