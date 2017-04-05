package org.team2471.frc.steamworks.commands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.lib.vector.Vector2;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

// this routine is used in conjunction with vision to drive to a detected target

public class DrivePathToTarget extends FollowPathTankDriveCommand {

  Path2D m_path;

  public DrivePathToTarget(double fps, double xPos, double yPos, double xTangent, double yTangent, double travelDirection) {  // speed is in FPS

    requires(drive);

    setSpeed(1.0);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);

    m_path = new Path2D();
    m_path.setTravelDirection(travelDirection);

    double feet = Vector2.length(new Vector2(xPos, yPos));
    double time = feet / fps;

    m_path.addPointAndTangent(0.0, 0.0, 0.0, feet / 2);  // use the full length of path for the tangent
    m_path.addPointAndTangent(xPos, yPos, xTangent, yTangent);  // should we normalize the tangent vector and determine the length here?

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(time, 1.0);

    setPath(m_path);
  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);  // this should be a robot.drive.lowGear() so we aren't guessing the behavior of the solenoid.
  }
}
