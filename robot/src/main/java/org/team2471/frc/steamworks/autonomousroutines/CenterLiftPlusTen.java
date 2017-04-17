package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.lib.motion_profiling.util.FollowPathTankDriveCommandBuilder;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommand;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommandOld;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;
//import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class CenterLiftPlusTen extends CommandGroup {
  public CenterLiftPlusTen() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new DriveDistanceCommand(6.0, 3.0));
    addParallel(new PlaceGearCommand(), 2);
    addSequential(new FollowPathTankDriveCommandBuilder(Robot.drive.tankDriveProfile)
        .withMirrored(mirrored)
        .withReversed(true)
        .withPointAndTangent(0, 0, 0, -2.0)
        .withPointAndTangent(3.5, -3.5, 2.0, 0.0)
        .withEasePoint(0, 0)
        .withEasePoint(2.5, 1.0)
        .build());
//    addSequential(new FollowPathTankDriveCommandBuilder(Robot.drive.tankDriveProfile)
//        .withMirrored(mirrored)
//        .withPointAndTangent(0, 0, 0, 0.1)
//        .withPointAndTangent(6, )
//        .build());
//    addSequential(new AimCommand(SmartDashboard.getNumber("RPM1",2471), (mirrored ? -1 : 1 )*150, 0.5, false));
  }
}
