package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.RetractHoodCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;
import org.team2471.frc.util.DelayedCommand;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class GearTen extends CommandGroup {
  public GearTen() {
    DriverStation driverStation = DriverStation.getInstance();

    Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == Alliance.Red;
    if (alliance == Alliance.Invalid) {
      throw new RuntimeException("Invalid alliance detected");
    }

    addSequential(new ExtendHoodCommand());
    addSequential(new DriveToLeftLift(1.0, mirrored));
    addSequential(new TiltGearIntakeCommand(), 0.8);
    addSequential(new DriveBackwardsFromLLToHopper(1.0, !mirrored));
    addParallel(new DelayedCommand(new TiltGearIntakeCommand(),2.0, 2.5));
    addSequential(new AimCommand());
    addSequential(new RetractHoodCommand());
  }
}
