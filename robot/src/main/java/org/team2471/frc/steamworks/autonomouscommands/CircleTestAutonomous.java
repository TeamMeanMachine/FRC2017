package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.Robot.drive;

public class CircleTestAutonomous extends FollowPathTankDriveCommand {

  Path2D m_path;

  public CircleTestAutonomous(double speed) {

    requires(drive);

    setSpeed(speed);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    m_path.setTravelDirection(1.0);
    m_path.setRobotWidth(36.5 / 12);

    m_path.addPointAndTangent(0.0, 0.0, 0.0, 4.5);
    m_path.addPointAndTangent(4.0, 4.0, 4.5, 0.0);
    m_path.addPointAndTangent(8.0, 0.0, 0.0, -4.5);
    m_path.addPointAndTangent(4.0, -4.0, -4.5, 0.0);
    m_path.addPointAndTangent(0.0, 0.0, 0.0, 4.5);
//    m_path.addPointAndTangent(4.0, 4.0, 4.5, 0.0);
//    m_path.addPointAndTangent(8.0, 0.0, 0.0, -4.5);
//    m_path.addPointAndTangent(4.0, -4.0, -4.5, 0.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(16.0, 1.0);

    setPath(m_path);
  }
}
