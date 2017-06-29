package org.team2471.frc.steamworks.autonomousroutines;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class TurnSharp extends FollowPathTankDriveCommand {

  Path2D m_path;

  public TurnSharp() {
    requires(drive);

    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    double size = 2.0;
    double tangent = 4.0;
    m_path.addPointAndTangent(0.0, 0.0, 0.0, tangent);
    m_path.addPointAndTangent(size, size, tangent, 0.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(8.0, 1.0);

//    double size = 0.5;
//    m_path.addPointAndTangent(0.0, 0.0, 0.0, size);
//    m_path.addPointAndTangent(size, size, size, 0.0);
//
//    m_path.addEasePoint(0.0, 0.0);
//    m_path.addEasePoint(2.0, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
