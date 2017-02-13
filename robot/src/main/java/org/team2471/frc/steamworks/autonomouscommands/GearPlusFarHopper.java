package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearPlusFarHopper extends CommandGroup {
  public GearPlusFarHopper() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == alliance.Blue){
      addSequential(new DriveToRightLift(1.0, false));
      addSequential(new DriveBackwardsFromRLToFarHopper(1.0, false));
      addSequential(new DriveToFarHopperFromRightLift(1.0, false));
    }
    else if (alliance == alliance.Red){
      addSequential(new DriveToRightLift(1.0, true));
      addSequential(new DriveBackwardsFromRLToFarHopper(1.0, true));
      addSequential(new DriveToFarHopperFromRightLift(1.0, true));
    }

  }
}
