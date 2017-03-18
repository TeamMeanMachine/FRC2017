package org.team2471.frc.util;

public class MeanMath {
  public static double min(double firstNum, double... additionalNums) {
    double result = firstNum;
    for (double num : additionalNums) {
      result = num < result ? num : result;
    }
    return result;
  }
}
