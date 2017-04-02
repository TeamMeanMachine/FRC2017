package org.team2471.frc.steamworks.autonomouscommands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class DriveToHopperAutoBlue extends FollowPathTankDriveCommand {
  Path2D m_path;

  public DriveToHopperAutoBlue(double speed, boolean mirror) {
    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();

    m_path.addPointAndTangent(0.0, 0.0, 0.0, 3.0);
    m_path.addPointAndTangent(-4.9, 6.7, -8.0, -0.1);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(3.0, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
