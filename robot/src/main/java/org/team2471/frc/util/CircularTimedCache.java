package org.team2471.frc.util;


import edu.wpi.first.wpilibj.Timer;

// Untested. I'm about 70% sure it doesn't work as is.
/**
 * @author Tyler Scheuble
 */
public class CircularTimedCache {
  private final double[] timestamps;
  private final double[] samples;
  private final int capacity;


  private double sampleRate; // samples per second
  private int size = 0;
  private int position = 0;

  public CircularTimedCache(int capacity, double estimatedSamplesPerSecond) {
    this.capacity = capacity;
    this.sampleRate = 1 / estimatedSamplesPerSecond;
    timestamps = new double[capacity];
    samples = new double[capacity];
  }

  public CircularTimedCache(int capacity) {
    this(capacity, 50);
  }

  public void add(double sample) {
    timestamps[position] = Timer.getFPGATimestamp();
    samples[position] = sample;
    position++;

    if(capacity > size) {
      size++;
    }

    if (position >= capacity) {
      position = 0;
    }
  }

  public double interpolate(double time) {
    double currentTime = Timer.getFPGATimestamp();
    double dt = currentTime - time;
    // first guess
    int index = position - (int) (dt * sampleRate);

    double smallSample = 0;
    double largeSample = 0;
    double smallTimestamp = 0;
    double largeTimestamp = 0;

    // wrap around
    if(index < 0) {
      index += size;
    }

    if(timestamps[index] < time) {
      // undershot; keep traversing forward until greater timestamp found
      while(timestamps[index] < time) {
        smallSample = samples[index];
        smallTimestamp = timestamps[index];
        // move forward
        index++;
        if(index == position) {
          // given time is more recent than any recorded sample
          // return most recent sample
          index -= 1;
          if(index < 0) {
            index += size;
          }
          return samples[index];
        } else if(index >= size) {
          // wrap around
          index = 0;
        }
      }
      // small sample has been found; large sample should be the next sample
      index ++;
      if(index == position) {
        return smallSample;
      } else if(index >= size) {
        index = 0;
      }
      largeSample = samples[index];
      largeTimestamp = timestamps[index];
    } else if(timestamps[index] > time) {
      // overshot; keep traversing backward until lesser timestamp found
      while(timestamps[index] > time) {
        // move backward
        index --;
        if(index == position) {
          // given time is older than any recorded sample
          // return oldest sample
          index += 1;
          if(index >= size) {
            index = 0;
          }
          return samples[index];
        } else if(index < 0) {
          // wrap around
          index += size;
        }
      }
    } else {
      return samples[index];
    }

    // linearly interpolate between samples
    double x = (dt - smallTimestamp) / largeTimestamp;
    return (largeSample - smallSample) * x + smallSample;
  }

  public int length() {
    return size;
  }
}
