package org.team2471.frc.steamworks.autonomousroutines;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class DriveEightFeet extends FollowPathTankDriveCommand {

  Path2D m_path;

  public DriveEightFeet() {
    requires(drive);

    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();

    m_path.addPoint(0.0, 0.0);
    m_path.addPoint(0.0, 8.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(2.0, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
