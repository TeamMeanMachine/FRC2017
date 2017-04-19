package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.DriveToHopperAutoBlue;
import org.team2471.frc.steamworks.autonomouscommands.FaceBoilerFromHopper;
import org.team2471.frc.steamworks.commands.*;

public class ForwardFortyKPAAuto extends CommandGroup {
  public ForwardFortyKPAAuto() {
    boolean mirrored = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red;

    addParallel(new ExtendFuelFlapCommand(), 15.0);
    addSequential(new ExtendHoodCommand());
    addSequential(new DriveToHopperAutoBlue(1.0, mirrored));
    addParallel(new ExtendHopperWallsCommand());
    addSequential(new WaitCommand(1.5));
    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
    addSequential(new FaceBoilerFromHopper(1.0, mirrored));
    addSequential(new TurnInPlaceCommand(-50, mirrored));
    addSequential(new AimCommand(mirrored ? -136 : 136, SmartDashboard.getNumber("RPM3", 2950), 1.0, true));
    addSequential(new RetractHoodCommand());
  }
}
