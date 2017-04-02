package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.FeederLiftInner;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class FeederLiftAuto extends CommandGroup {
  public FeederLiftAuto() {
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new FeederLiftInner(1.0, mirrored));
    addSequential(new PlaceGearCommand());
    //addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
