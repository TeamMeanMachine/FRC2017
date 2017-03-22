package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.BoilerLiftInner;

public class BoilerLiftAuto extends CommandGroup {
  public BoilerLiftAuto(){
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new BoilerLiftInner(1.0, mirrored));
   // addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
