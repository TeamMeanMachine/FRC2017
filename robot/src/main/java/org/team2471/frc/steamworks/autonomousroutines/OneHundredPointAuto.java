package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team2471.frc.steamworks.autonomouscommands.BoilerLiftInner;
import org.team2471.frc.steamworks.autonomouscommands.DriveBackwardsFromHopper;
import org.team2471.frc.steamworks.autonomouscommands.DriveBackwardsFromBoilerLiftToHopper;
import org.team2471.frc.steamworks.autonomouscommands.DriveToHopperFromBoilerLift;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.RetractHoodCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;
import org.team2471.frc.util.DelayedCommand;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class OneHundredPointAuto extends CommandGroup {
  public OneHundredPointAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == Alliance.Red;
    if (alliance == Alliance.Invalid) {
      throw new RuntimeException("Invalid alliance detected");
    }

    addSequential(new ExtendHoodCommand());
    addSequential(new BoilerLiftInner(1.0, mirrored));
    addParallel(new TiltGearIntakeCommand(), 9.8);
    addSequential(new WaitCommand(2.8));
    addSequential(new DriveBackwardsFromBoilerLiftToHopper(1.0, mirrored));
//    addParallel(new DelayedCommand(new TiltGearIntakeCommand(),1.0, 2.5));
    addSequential(new DriveToHopperFromBoilerLift(1.0, mirrored));
    addSequential(new WaitCommand(2.0));
    addSequential(new DriveBackwardsFromHopper(1.0, mirrored));
//    addSequential(new AimCommand());
    addSequential(new RetractHoodCommand());
  }
}
