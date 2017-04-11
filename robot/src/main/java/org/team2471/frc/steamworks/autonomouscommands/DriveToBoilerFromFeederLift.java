package org.team2471.frc.steamworks.autonomouscommands;


import edu.wpi.first.wpilibj.DriverStation;
import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

public class DriveToBoilerFromFeederLift extends FollowPathTankDriveCommand {
  Path2D m_path;

  public DriveToBoilerFromFeederLift(double speed) {
    boolean mirror = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red; // mirror if red alliance

    requires(drive);

    setSpeed(speed);
    setMirrorPath(mirror);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    m_path.setTravelDirection(1.0);

    m_path.addPointAndTangent(0.0, 0.0, 5.0, 0.5);
    m_path.addPointAndTangent(-14.5, -1.0, 4.0, 1.0);

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(6.0, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
