package org.team2471.frc.steamworks.autonomouscommands;


import org.team2471.frc.lib.motion_profiling.FollowPathTankDriveCommand;
import org.team2471.frc.lib.motion_profiling.Path2D;
import org.team2471.frc.steamworks.HardwareMap;

import static org.team2471.frc.steamworks.HardwareMap.DriveMap.shiftPTO;
import static org.team2471.frc.steamworks.Robot.drive;

//this autonomous works only if robot is right in front of the lift(in a straight line)
public class ForwardForFortyKPA extends FollowPathTankDriveCommand{

    Path2D m_path;


    public ForwardForFortyKPA(double speed, boolean mirror){

        requires(drive);

        setSpeed( speed );
        setMirrorPath(mirror);
        setLeftController(HardwareMap.DriveMap.leftMotor1);
        setRightController(HardwareMap.DriveMap.rightMotor1);

        m_path = new Path2D();
        m_path.setTravelDirection(1.0);

//        m_path.addPointAndTangent( 0.0, 0.0, -0.125, -0.5);
//        m_path.addPointAndTangent( 0.2, 0.5, 0.125, -0.25);
        m_path.addPointAndTangent( 0.0, 0.0, 0.2, -1.0);
        m_path.addPointAndTangent( 0.0, -0.85, 0.0, -1.0);

        m_path.addEasePoint( 0.0, 0.0 );
        m_path.addEasePoint( 1.0, 1.0 );


        setPath( m_path );
    }
    @Override
    protected void initialize() {
        super.initialize();
        shiftPTO.set(true);
    }
}
