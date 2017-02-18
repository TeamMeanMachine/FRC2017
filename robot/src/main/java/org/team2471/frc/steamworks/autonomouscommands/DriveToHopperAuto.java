package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperAuto extends CommandGroup {
  public DriveToHopperAuto(){

  DriverStation driverStation = DriverStation.getInstance();

  DriverStation.Alliance alliance = driverStation.getAlliance();
  int location = driverStation.getLocation();

    if (alliance == DriverStation.Alliance.Blue) {
      addSequential(new DriveToHopperAutoBlue(1.0));
    }
    else if (alliance == DriverStation.Alliance.Red){
      addSequential(new DriveToHopperAutoRed(1.0));
    }
  }
}
