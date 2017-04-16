package com.team254.frc2016.vision;

/**
 * A basic interface for classes that get VisionUpdates. Classes that implement
 * this interface specify what to do when VisionUpdates are received.
 *
 * @see VisionUpdate
 */
public interface VisionUpdateReceiver {
  void gotUpdate(VisionUpdate update);
}