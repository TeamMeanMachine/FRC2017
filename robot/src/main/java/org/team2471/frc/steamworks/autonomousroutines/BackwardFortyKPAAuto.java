package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2A;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2BBlue;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2BRed;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.ExtendHopperWallsCommand;
import org.team2471.frc.steamworks.commands.SpinUpShooterCommand;
import org.team2471.frc.util.DelayedCommand;

public class BackwardFortyKPAAuto extends CommandGroup {
  public BackwardFortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new BackwardsAuto2A(1.0, mirrored));
    addParallel(new DelayedCommand(new ExtendHopperWallsCommand(), 1));
    addParallel(new ExtendHoodCommand());
    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
    addSequential(mirrored ?
        new BackwardsAuto2BRed(1.0, true) :
        new BackwardsAuto2BBlue(1.0, false));
    addSequential(new AimCommand(13, SmartDashboard.getNumber("RPM1", 2550), 1.0, true));
  }
}
