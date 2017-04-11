package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.BoilerLiftInner;
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class BoilerLiftAuto extends CommandGroup {
  public BoilerLiftAuto() {
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new BoilerLiftInner(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 1.0);
    addSequential(new DriveDistanceCommand(-2, 1));
  }
}
