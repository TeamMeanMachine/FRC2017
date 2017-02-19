package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class SecondOneHundredPointAuto extends CommandGroup {
  public SecondOneHundredPointAuto(){
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    addParallel(new TiltGearIntakeCommand(), 6.0);
    if(alliance == alliance.Blue){
      addSequential(new DriveToHopperAutoBlue(1.0));
      addSequential(new WaitCommand(3.0));
      addSequential(new DriveBackwardsFromHopper(1.0, false));
 //     addSequential(new DriveBackwardsFromLLToHopper(1.0, false));
    }
    if(alliance == alliance.Red){
      addSequential(new DriveToHopperAutoRed(1.0));
      addSequential(new DriveBackwardsFromHopper(1.0, true));
    }

  }
}
