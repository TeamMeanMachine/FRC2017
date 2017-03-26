package org.team2471.frc.steamworks;

import java.util.ArrayList;

public class CircularDataFilter {

  private ArrayList<Double> values;
  private int capacity;
  private int currentPos;
  private int currentSize;
  private double sum;

  public CircularDataFilter(int capacity ) {
    this.capacity = capacity;
    currentPos = 0;
    currentSize = 0;
    values = new ArrayList<>(capacity);
  }

  public void add( double timeStamp, double value ) {
    if (currentSize<capacity) {
      currentSize++;
    }
    else {
      sum -= values.get(currentPos);
    }

    values.set(currentPos, value);
    sum += value;

    currentPos++;
    if (currentPos==capacity) {
      currentPos = 0;
    }
  }

  public double average() {
    return sum / currentSize;
  }

  public double getValueAt(int index) {  // 0 is current position, 2 is two samples ago
    int pos = currentPos - index;
    if (pos>=currentSize) {
      pos = currentSize - 1;
    }

    if (pos >= 0) {
      return values.get(pos);
    }
    else  {
      return values.get( capacity + pos );  // pos represents how far from end to sample
    }
  }
}
