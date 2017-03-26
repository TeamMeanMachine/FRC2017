package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.FortyKPA;
import org.team2471.frc.steamworks.autonomouscommands.ForwardForFortyKPA;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.SpinUpShooterCommand;

public class BackwardFortyKPAAuto extends CommandGroup {
  public BackwardFortyKPAAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new FortyKPA(1.0, mirrored));
//    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
//    addSequential(new ForwardForFortyKPA(1.0, mirrored));
//    addParallel(new ExtendHoodCommand());
//    addSequential(new AimCommand(0));
  }
}
