package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.Robot.drive;

public class FeederLiftInner extends FollowPathTankDriveCommand {

  Path2D m_path;

  public FeederLiftInner(double speed, boolean mirror) {

    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    m_path.setTravelDirection(1.0);

    m_path.addPointAndTangent(0.0, 0.0, 0.0, 6.0);
    m_path.addPointAndTangent(-6.5, 8.5, -12.0, 4.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(4.0, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.lowGear();
  }
}


