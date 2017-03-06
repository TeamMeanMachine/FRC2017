package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
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

    addSequential(new DriveToLeftLift(1.0, mirrored));
    addSequential(new TiltGearIntakeCommand(), 0.8);
    addSequential(new PrintCommand("Finished first drive"));
    addSequential(new DriveBackwardsFromLLToHopper(1.0, mirrored));
    addSequential(new PrintCommand("Finished driving backwards"));
    addParallel(new DelayedCommand(new TiltGearIntakeCommand(),2.0, 2.5));
    addSequential(new DriveToHopperFromLeftLift(1.0, mirrored));
    addSequential(new WaitCommand(2.0));
    addSequential(new DriveBackwardsFromHopper(1.0, mirrored));
    addSequential(new ExtendHoodCommand());
    addSequential(new PrintCommand("Done!"));
    addSequential(new AimCommand());
    addSequential(new RetractHoodCommand());
  }
}
