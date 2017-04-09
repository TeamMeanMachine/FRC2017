package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class FeederLiftAuto extends CommandGroup {
  public FeederLiftAuto() {
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new FeederLiftInner(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 2.0);
    addSequential(new FeederLiftB(1.0, mirrored));
    addSequential(new FeederLiftC(1.0, mirrored));

    //addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
