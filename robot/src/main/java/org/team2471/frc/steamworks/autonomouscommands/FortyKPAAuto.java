package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.AimCommand;

public class FortyKPAAuto extends CommandGroup {
  public FortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == alliance.Blue) {
      addSequential(new FortyKPA(1.0, true));
      addSequential(new ForwardForFortyKPA(1.0, true));
      addSequential(new AimCommand());
    }
    if (alliance == alliance.Red) {
      addSequential(new FortyKPA(1.0, false));
      addSequential(new ForwardForFortyKPA(1.0, false));
      addSequential(new AimCommand());
    }
  }
}
