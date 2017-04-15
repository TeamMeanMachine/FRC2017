package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.BoilerLiftInner;
import org.team2471.frc.steamworks.autonomouscommands.OneHundredAutoB;
import org.team2471.frc.steamworks.autonomouscommands.OneHundredAutoC;
import org.team2471.frc.steamworks.commands.*;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class OneHundredPointAuto extends CommandGroup {
  public OneHundredPointAuto() {
    DriverStation driverStation = DriverStation.getInstance();

    Alliance alliance = driverStation.getAlliance();

    boolean mirrored = alliance == Alliance.Red;
    if (alliance == Alliance.Invalid) {
      throw new RuntimeException("Invalid alliance detected");
    }

//    addSequential(new ExtendHoodCommand());
    addSequential(new BoilerLiftInner(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 1.0);
    addSequential(new OneHundredAutoB(1.0, mirrored));
//    addParallel(new IntakeFuelCommand(), 5.0);
//    addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 2550)));
//    addParallel(new ExtendHopperWallsCommand()); // no movable walls on practice bot

//    addSequential(new BackwardsAuto2B(1.0, mirrored));
//
    addSequential(new OneHundredAutoC(1.0, mirrored));
//    addSequential(new AimCommand(mirrored ? -188.5 : 184.2, SmartDashboard.getNumber("RPM1", 2550)));
  }
}
