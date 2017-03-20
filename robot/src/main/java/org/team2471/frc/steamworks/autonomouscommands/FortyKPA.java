package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class FortyKPA extends FollowPathTankDriveCommand {

  Path2D m_path;

  public FortyKPA(double speed, boolean mirror ) {
    requires(drive);

    setSpeed( speed );
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    m_path.setTravelDirection(-1.0);

    m_path.addPointAndTangent(0.0, 0.0, 0.0, 3.0);
    m_path.addPointAndTangent(0.5, 2.75, 0.25, 1.375);
    m_path.addPointAndTangent(1.0, 5.5, 0.30, 1.375);
    m_path.addPointAndTangent(2.45, 8.7, 0.25, 1.0);

    m_path.addEasePoint( 0.0, 0.0 );
    m_path.addEasePoint( 3.0, 1.0 );

    setPath( m_path );
  }

  @Override
  protected void initialize() {
    super.initialize();
    //System.out.println("Got To Drive Six Feet Initialize");
    shiftPTO.set(true);
  }
}
