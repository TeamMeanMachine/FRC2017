package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.DriveAwayFromBoiler;
import org.team2471.frc.steamworks.autonomouscommands.DriveToLiftFromBoiler;
import org.team2471.frc.steamworks.commands.AimCommand;

public class BoilerGearAuto extends CommandGroup {
  public BoilerGearAuto() {
    boolean mirrored = DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red;

    addSequential(new AimCommand(), 3);
    addSequential(new DriveAwayFromBoiler(1, mirrored));
    addSequential(new DriveToLiftFromBoiler(1, mirrored));
   // addSequential(new TiltGearIntakeCommand(), 7);
  }
}
