package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team2471.frc.steamworks.autonomouscommands.BoilerLiftInner;
import org.team2471.frc.steamworks.autonomouscommands.FaceBoilerFromBoilerLift;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;
import org.team2471.frc.steamworks.commands.RetractHoodCommand;
import org.team2471.frc.util.DelayedCommand;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class GearTenAuto extends CommandGroup {
  public GearTenAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == Alliance.Red;
    if (alliance == Alliance.Invalid) {
      throw new RuntimeException("Invalid alliance detected");
    }

    addSequential(new ExtendHoodCommand());
    addSequential(new BoilerLiftInner(1.0, mirrored));
    addSequential(new PlaceGearCommand());
   // addParallel(new TiltGearIntakeCommand(), 5);
    addSequential(new WaitCommand(2.7));
    addSequential(new FaceBoilerFromBoilerLift(1.0, mirrored));
   // addParallel(new DelayedCommand(new TiltGearIntakeCommand(),2.0, 2.5));
//    addSequential(new AimCommand());
    addSequential(new RetractHoodCommand());
  }
}
