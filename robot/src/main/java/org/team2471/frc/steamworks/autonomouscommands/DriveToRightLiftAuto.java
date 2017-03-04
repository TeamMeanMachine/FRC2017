package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

/**
 * Created by Team Mean Machine on 2/18/2017.
 */
public class DriveToRightLiftAuto extends CommandGroup {
  public DriveToRightLiftAuto(){
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == alliance.Blue){
      addSequential(new DriveToLeftLift(1.0, false));
      addSequential(new TiltGearIntakeCommand(), 2.0);

    }
    if (alliance == alliance.Red);
    addSequential(new DriveToLeftLift(1.0, true));
    addSequential(new TiltGearIntakeCommand(), 2.0);

  }
}
