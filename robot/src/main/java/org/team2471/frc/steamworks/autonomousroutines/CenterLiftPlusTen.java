package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.FaceBoilerFromMiddleLift;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;

public class CenterLiftPlusTen extends CommandGroup {
  public CenterLiftPlusTen(){
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == alliance.Red){
      addSequential(new DriveToMiddleLift());
      addSequential(new FaceBoilerFromMiddleLift(1.0, true));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand());
    }
    if (alliance == alliance.Blue){
      addSequential(new DriveToMiddleLift());
      addSequential(new FaceBoilerFromMiddleLift(1.0, false));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand());
    }
  }
}
