package org.team2471.frc.steamworks.autonomouscommands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.Robot.drive;

public class OneHundredAutoB extends FollowPathTankDriveCommand {
  Path2D m_path;

  public OneHundredAutoB(double speed, boolean mirror) {

    requires(drive);
    m_path = new Path2D();
    setPath(m_path);
    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);


    m_path.setTravelDirection(-1.0);

    m_path.addPointAndTangent(7.0, 8.85, -6.0, -3.0);
    m_path.addPointAndTangent(2.0, 14.5, 1.0, 2.5);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(2.4, 1.0);

  }

  @Override
  protected void initialize() {
    super.initialize();
    Robot.drive.hiGear();
  }
}

