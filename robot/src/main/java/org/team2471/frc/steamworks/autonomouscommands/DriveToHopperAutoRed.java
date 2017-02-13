package org.team2471.frc.steamworks.autonomouscommands;

import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;


import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftSolenoid;
import static org.team2471.frc.steamworks.Robot.drive;

public class DriveToHopperAutoRed extends FollowPathTankDriveCommand {
  Path2D m_path;

  public DriveToHopperAutoRed(double speed ) {
    requires(drive);

    setSpeed( speed );
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();

    m_path.addPointAndTangent(0.0,0.0, 0.0, 3.0 );
    m_path.addPointAndTangent(3.3,6.5, 10.0, 0.0 );

    m_path.addEasePoint( 0.0, 0.0 );
    m_path.addEasePoint( 4.0, 1.0 );

    setPath( m_path );
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftSolenoid.set(true);
  }
}
