package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.FortyKPA;
import org.team2471.frc.steamworks.autonomouscommands.ForwardForFortyKPA;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendFuelFlapCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.SpinUpShooterCommand;

public class FortyKPAAuto extends CommandGroup {
  public FortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();
    int location = driverStation.getLocation();

    if (alliance == DriverStation.Alliance.Red) {
      addParallel(new ExtendFuelFlapCommand(), 15.0);
      addSequential(new FortyKPA(1.0, true));
      addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM4", 2700)));
      addSequential(new ForwardForFortyKPA(1.0, true));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand(0, SmartDashboard.getNumber("RPM4", 2700)));
    }
    if (alliance == DriverStation.Alliance.Blue) {
      addParallel(new ExtendFuelFlapCommand(), 15.0);
      addSequential(new FortyKPA(1.0, false));
      addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM4", 2700)));
      addSequential(new ForwardForFortyKPA(1.0, false));
      addSequential(new ExtendHoodCommand());
      addSequential(new AimCommand(0, SmartDashboard.getNumber("RPM4", 2700)));
    }
  }
}
