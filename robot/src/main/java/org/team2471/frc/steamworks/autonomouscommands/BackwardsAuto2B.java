package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class BackwardsAuto2B extends FollowPathTankDriveCommand {
  private Path2D path;

  public BackwardsAuto2B(double speed, boolean mirror) {
    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    path = new Path2D();
    path.setTravelDirection(1.0);

    path.addPointAndTangent(-1.5, 11.5, 0.0, -0.5);
    path.addPointAndTangent(-3.4, 7.0, -0.2, -1.0);

    path.addEasePoint(0.0, 0.0);
    path.addEasePoint(1.3, 1.0);

    setPath(path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.hiGear();
  }
}
