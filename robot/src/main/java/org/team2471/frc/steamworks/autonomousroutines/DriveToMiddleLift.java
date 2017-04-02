package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.DriveToMiddleLiftInner;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class DriveToMiddleLift extends CommandGroup {
  public DriveToMiddleLift() {
    DriverStation driverStation = DriverStation.getInstance();

    addSequential(new DriveToMiddleLiftInner(1.0));
    addSequential(new PlaceGearCommand());
    //addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
