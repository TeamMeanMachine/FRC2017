package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team2471.frc.steamworks.autonomouscommands.DriveToHopperAutoBlue;
import org.team2471.frc.steamworks.autonomouscommands.FaceBoilerFromHopper;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.RetractHoodCommand;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class DriveToHopperAuto extends CommandGroup {
  public DriveToHopperAuto(){
    boolean mirrored = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red;

    addParallel(new TiltGearIntakeCommand(), 5.0);
    addSequential(new ExtendHoodCommand());
    addSequential(new DriveToHopperAutoBlue(1.0, mirrored));
    addSequential(new WaitCommand(2));
    addSequential(new FaceBoilerFromHopper(1.0, mirrored));
    addSequential(new AimCommand());
    addSequential(new RetractHoodCommand());
  }
}
