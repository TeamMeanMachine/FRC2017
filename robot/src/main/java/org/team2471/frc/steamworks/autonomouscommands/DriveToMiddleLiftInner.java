package org.team2471.frc.steamworks.autonomouscommands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

//this autonomous works only if robot is right in front of the lift(in a straight line)
public class DriveToMiddleLiftInner extends FollowPathTankDriveCommand {

  Path2D m_path;

  public DriveToMiddleLiftInner(double speed) {

    requires(drive);
    m_path = new Path2D();
    setPath(m_path);
    setSpeed(speed);
    setLeftController(HardwareMap.DriveMap.leftMotor1);
    setRightController(HardwareMap.DriveMap.rightMotor1);


    m_path.setTravelDirection(1.0);

    m_path.addPoint(0.0, 0.0);
    m_path.addPoint(0.0, 7.4);
    //Change to farther lengths for actual robot since we are testing on Thunder, For all Autonomi

    m_path.addEasePoint(0.0, 0.0);
    m_path.addEasePoint(3.0, 1.0);


  }

  @Override
  protected void initialize() {
    super.initialize();
    shiftPTO.set(true);
  }
}
