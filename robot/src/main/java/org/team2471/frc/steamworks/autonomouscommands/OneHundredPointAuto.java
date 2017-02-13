package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class OneHundredPointAuto extends CommandGroup {
  public OneHundredPointAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == Alliance.Blue) {
      addSequential(new DriveToLeftLift(1.0, false));
      addSequential(new PrintCommand("Finished first drive"));
      addSequential(new DriveBackwardsFromLLToHopper(1.0, false));
      addSequential(new PrintCommand("Finished driving backwards"));
      addSequential(new DriveToHopperFromLeftLift(1.0,false));
      addSequential(new PrintCommand("Done!"));
    } else if (alliance == Alliance.Red) {
      addSequential(new DriveToLeftLift(1.0, true));
      addSequential(new PrintCommand("Finished First drive"));
      addSequential(new DriveBackwardsFromLLToHopper(1.0, true));
      addSequential(new PrintCommand("Finished driving backwards"));
      addSequential(new DriveToHopperFromLeftLift(1.0, true));
      addSequential(new PrintCommand("Done!"));
    }
  }
}
