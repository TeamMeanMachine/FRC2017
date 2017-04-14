package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommand;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;
import org.team2471.frc.steamworks.commands.TurnInPlaceCommand;
//import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class CenterLiftPlusTen extends CommandGroup {
  public CenterLiftPlusTen() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new DriveDistanceCommand(5.5, 2.2));
    addParallel(new PlaceGearCommand(), 2);
    addSequential(new DriveDistanceCommand(-2.0, 1));
    addSequential(new TurnInPlaceCommand(-100.0, mirrored));
    addSequential(new DriveDistanceCommand(8.0, 2));
    addSequential(new AimCommand(SmartDashboard.getNumber("RPM1",2471), (mirrored ? -1 : 1 )*150, 0.5));
  }
}
