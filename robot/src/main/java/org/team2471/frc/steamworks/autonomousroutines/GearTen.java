package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.commands.ExtendHoodCommand;
import org.team2471.frc.steamworks.commands.ExtendHopperWallsCommand;
import org.team2471.frc.steamworks.commands.IntakeFuelCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;
import org.team2471.frc.util.WaitingOnHardware;

@Deprecated
public class GearTen extends CommandGroup {
  public GearTen() {
    DriverStation driverStation = DriverStation.getInstance();

    DriverStation.Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == DriverStation.Alliance.Red;
    if (alliance == DriverStation.Alliance.Invalid) {
      throw new RuntimeException("Invalid alliance detected");
    }

    addSequential(new ExtendHoodCommand());
    addSequential(new BoilerLiftInnerFar(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 1.0);
    addSequential(new DriveDistanceCommand(-2, 1));
//    addSequential(new OneHundredAutoB(1.0, mirrored));
//    addParallel(new ExtendHopperWallsCommand());
//    addParallel(new IntakeFuelCommand(), 2.0);
//    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
//    addSequential(new BackwardsAuto2B(1, mirrored));
//    addSequential(new AimCommand());
  }

}
