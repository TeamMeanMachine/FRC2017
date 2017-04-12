package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.FaceBoilerFromFeederLift;
import org.team2471.frc.steamworks.autonomouscommands.FeederLiftB;
import org.team2471.frc.steamworks.autonomouscommands.FeederLiftC;
import org.team2471.frc.steamworks.autonomouscommands.FeederLiftInner;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class FeederLiftPlusTen extends CommandGroup {
  public FeederLiftPlusTen() {
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new FeederLiftInner(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 2.0);
    addSequential(new FaceBoilerFromFeederLift(1.0, mirrored));
    addSequential(new FaceBoilerFromFeederLift(1.0, mirrored));

    //addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
