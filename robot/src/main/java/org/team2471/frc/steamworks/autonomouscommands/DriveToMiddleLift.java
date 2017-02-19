package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

/**
 * Created by Team Mean Machine on 2/18/2017.
 */
public class DriveToMiddleLift extends CommandGroup {
  public DriveToMiddleLift(){
    DriverStation driverStation = DriverStation.getInstance();

      addSequential(new DriveToLift(1.0));
      addSequential(new TiltGearIntakeCommand(), 2.0);


  }
}
