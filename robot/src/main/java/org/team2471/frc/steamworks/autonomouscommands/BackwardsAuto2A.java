package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class BackwardsAuto2A extends FollowPathTankDriveCommand {
  private Path2D path;

  public BackwardsAuto2A(double speed, boolean mirror) {
    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    path = new Path2D();
    path.setTravelDirection(-1.0);

    path.addPointAndTangent(0.0, 0.0, 0.0, 5.0);
    path.addPointAndTangent(-2.5, 11.5, 0.0, 5.0);

    path.addEasePoint(0.0, 0.0);
    path.addEasePoint(2.5, 1.0);

    setPath(path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
