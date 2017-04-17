package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.DriveTwoPointCurve;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

public class DriveDistanceCommandOld extends DriveTwoPointCurve {
  public DriveDistanceCommandOld(double distance, double time) {
    super(0, 0, 0, distance / 3, 0, distance, 0, distance / 3, time, false, Math.signum(distance),
        HardwareMap.DriveMap.leftMotor1, HardwareMap.DriveMap.rightMotor1);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.lowGear();
  }
}
