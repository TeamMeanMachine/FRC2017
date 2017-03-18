package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CoOpHopper extends CommandGroup {
  public CoOpHopper() {
  DriverStation driverStation = DriverStation.getInstance();

  DriverStation.Alliance alliance = driverStation.getAlliance();
  int location = driverStation.getLocation();

    if (alliance == DriverStation.Alliance.Blue) {
      addSequential(new CoOpHopperAuto(1.0, false));
    }
    else if (alliance == DriverStation.Alliance.Red){
      addSequential(new CoOpHopperAuto(1.0, true));
    }
  }
}
