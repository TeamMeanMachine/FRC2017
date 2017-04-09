package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.Robot.drive;

public class OneHundredAutoC extends FollowPathTankDriveCommand {
  private Path2D path;

  public OneHundredAutoC(double speed, boolean mirror) {
    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    path = new Path2D();
    path.setTravelDirection(1.0);

    path.addPointAndTangent(0, 0, 0.0, -1.0);
    path.addPointAndTangent(-2.0, -5.5, 0.0, -2.0);

    path.addEasePoint(0.0, 0.0);
    path.addEasePoint(2.0, 1.0);

    setPath(path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.lowGear();
  }
}
