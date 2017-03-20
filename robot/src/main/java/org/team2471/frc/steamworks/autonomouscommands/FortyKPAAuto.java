package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class FortyKPAAuto extends CommandGroup {
  public FortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == alliance.Red) {
      addSequential(new FortyKPA(1.0, true));
      addSequential(new ForwardForFortyKPA(1.0, true));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand());
    }
    if (alliance == alliance.Blue) {
      addSequential(new FortyKPA(1.0, false));
      addSequential(new ForwardForFortyKPA(1.0, false));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand());
    }
  }
}
