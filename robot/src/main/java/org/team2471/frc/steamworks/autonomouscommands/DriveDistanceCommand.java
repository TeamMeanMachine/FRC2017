package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.DriveTwoPointCurve;
import org.team2471.frc.steamworks.HardwareMap;

public class DriveDistanceCommand extends DriveTwoPointCurve {
  public DriveDistanceCommand(double distance, double time) {
    super(0, 0, 0, distance / 3, 0, distance, 0, distance / 3, time, false, Math.signum(distance),
        HardwareMap.DriveMap.leftMotor1, HardwareMap.DriveMap.rightMotor1);
  }
}
