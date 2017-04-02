package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class DriveForwardCommand extends FollowPathTankDriveCommand {
  private final Path2D m_path;

  public DriveForwardCommand(double distance, double time) {
    requires(drive);

    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();

    m_path.addPoint(0.0,0.0 );
    m_path.addPoint(0.0, distance);

    m_path.addEasePoint( 0.0, 0.0 );
    m_path.addEasePoint( time, 1.0 );

    setPath( m_path );
  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.lowGear();
  }
}
