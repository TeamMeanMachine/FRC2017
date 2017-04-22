package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.Robot.drive;

public class BackwardsAuto2BBlue extends FollowPathTankDriveCommand {
  private Path2D path;

  public BackwardsAuto2BBlue(double speed, boolean mirror) {
    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    path = new Path2D();
    path.setTravelDirection(1.0);

    path.addPointAndTangent(-1.65, 11.0, 0.0, -1.0);
    path.addPointAndTangent(-3.0, 6.0, 0.0, -2.0);

    path.addEasePoint(0.0, 0.0);
    path.addEasePoint(1.5, 1.0);

    setPath(path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.hiGear();
  }
}
