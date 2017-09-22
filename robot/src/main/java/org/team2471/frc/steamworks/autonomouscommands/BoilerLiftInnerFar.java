package org.team2471.frc.steamworks.autonomouscommands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class BoilerLiftInnerFar extends FollowPathTankDriveCommand {

  Path2D m_path;

  public BoilerLiftInnerFar(double speed, boolean mirror) {
    requires(drive);

    m_path = new Path2D();
    setPath(m_path);
    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);


    m_path.setTravelDirection(1.0);

    m_path.addPointAndTangent(3.5, 0.0, 0.0, 2.0);
    m_path.addPointAndTangent(6, 7.3, 6.0, 3.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(2.3, 1.0);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}


