package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2A;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2B;
import org.team2471.frc.steamworks.commands.ExtendFuelFlapCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.ExtendHopperWallsCommand;

public class ForwardsFortyKPAAuto2 extends CommandGroup {
  public ForwardsFortyKPAAuto2() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;

//    addParallel(new DelayedCommand(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)), 3));
    addSequential(new BackwardsAuto2A(1.0, mirrored));
    addParallel(new ExtendHopperWallsCommand());
    addParallel(new ExtendFuelFlapCommand());
    addSequential(new BackwardsAuto2B(1.0, mirrored));
    addParallel(new ExtendHoodCommand());
//    addSequential(new AimCommand(13, SmartDashboard.getNumber("RPM1", 2550)));
  }
}
