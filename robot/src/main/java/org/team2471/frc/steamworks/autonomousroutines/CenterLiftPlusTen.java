package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.motion_profiling.util.FollowPathTankDriveCommandBuilder;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommand;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommandOld;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;
import org.team2471.frc.steamworks.commands.SpinUpShooterCommand;
import org.team2471.frc.steamworks.commands.TurnInPlaceCommand;
//import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class CenterLiftPlusTen extends CommandGroup {
  public CenterLiftPlusTen() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new DriveDistanceCommand(6.0, 2.0));
    addParallel(new PlaceGearCommand(), 2.0);
    addSequential(new FollowPathTankDriveCommandBuilder(Robot.drive.tankDriveProfile)
        .withMirrored(mirrored)
        .withReversed(true)
        .withPointAndTangent(0, 0, 0, -4.0)
        .withPointAndTangent(5.0, -2.5, 4.0, 1.0)
        .withEasePoint(0, 0)
        .withEasePoint(2.0, 1.0)
        .build());
    addSequential(new DriveDistanceCommand(10.0, 2.0));
    addSequential(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2471)));
    addSequential(new TurnInPlaceCommand(-40, mirrored));
    addSequential(new AimCommand(SmartDashboard.getNumber("RPM1",2471), (mirrored ? -1 : 1 )*150, 0.5, false));
  }
}
