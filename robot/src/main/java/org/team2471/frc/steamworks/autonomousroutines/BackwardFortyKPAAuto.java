package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.DriveForwardCommand;
import org.team2471.frc.steamworks.autonomouscommands.FortyKPA;
import org.team2471.frc.steamworks.commands.*;
import org.team2471.frc.util.DelayedCommand;

public class BackwardFortyKPAAuto extends CommandGroup {
  public BackwardFortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addParallel(new DelayedCommand(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)), 3));
    addSequential(new FortyKPA(1.0, mirrored));
    addParallel(new ExtendHopperWallsCommand());
    addSequential(new TurnInPlaceCommand(30, mirrored), 1);
    addSequential(new DriveForwardCommand(2, 1.0));
//    addSequential(new TurnInPlaceCommand(7, mirrored), 1);
    addParallel(new ExtendHoodCommand());
//    addSequential(new ForwardForFortyKPA(1.0, mirrored));
    addSequential(new AimCommand(13, SmartDashboard.getNumber("RPM1", 2550)));
  }
}
