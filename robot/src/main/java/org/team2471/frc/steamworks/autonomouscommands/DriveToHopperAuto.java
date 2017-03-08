package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.RetractHoodCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class DriveToHopperAuto extends CommandGroup {
  public DriveToHopperAuto(){

    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    addParallel(new TiltGearIntakeCommand(), 5.0);
    if (alliance == DriverStation.Alliance.Blue) {
      addSequential(new ExtendHoodCommand());
      addSequential(new DriveToHopperAutoBlue(1.0, false));
      addSequential(new AimCommand());
      addSequential(new RetractHoodCommand());
    }
    else if (alliance == DriverStation.Alliance.Red){
      addSequential(new ExtendHoodCommand());
      addSequential(new DriveToHopperAutoBlue(1.0, true));
      addSequential(new AimCommand());
      addSequential(new RetractHoodCommand());
    }
  }
}
