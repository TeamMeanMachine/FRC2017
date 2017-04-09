package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2A;
import org.team2471.frc.steamworks.autonomouscommands.BackwardsAuto2B;
import org.team2471.frc.steamworks.commands.*;

public class BackwardFortyKPAAuto2 extends CommandGroup {
  public BackwardFortyKPAAuto2() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new BackwardsAuto2A(1.0, mirrored));
    addParallel(new ExtendHopperWallsCommand());
    addParallel(new ExtendHoodCommand());
    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
    addSequential(new BackwardsAuto2B(1.0, mirrored));
    addSequential(new AimCommand(13, SmartDashboard.getNumber("RPM1", 2550), 1.0));
  }
}
