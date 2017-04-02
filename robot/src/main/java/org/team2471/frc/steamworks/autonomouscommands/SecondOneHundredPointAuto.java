package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class SecondOneHundredPointAuto extends CommandGroup {
  public SecondOneHundredPointAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    //addParallel(new TiltGearIntakeCommand(), 6.0);
    if (alliance == DriverStation.Alliance.Blue) {
      addSequential(new DriveToHopperAutoBlue(1.0, false));
      addSequential(new WaitCommand(2.0));
      addSequential(new DriveBackwardsFromHopper(1.0, false));
      //     addSequential(new DriveBackwardsFromBoilerLiftToHopper(1.0, false));
    }
    if (alliance == DriverStation.Alliance.Red) {
      addSequential(new DriveToHopperAutoBlue(1.0, true));
      addSequential(new WaitCommand(2.0));
      addSequential(new DriveBackwardsFromHopper(1.0, true));
    }

  }
}
