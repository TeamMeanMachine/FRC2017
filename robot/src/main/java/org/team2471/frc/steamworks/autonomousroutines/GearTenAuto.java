package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.commands.*;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

public class GearTenAuto extends CommandGroup {
  public GearTenAuto() {
      DriverStation driverStation = DriverStation.getInstance();

      DriverStation.Alliance alliance = driverStation.getAlliance();

      boolean mirrored = alliance == DriverStation.Alliance.Red;
      if (alliance == DriverStation.Alliance.Invalid) {
        throw new RuntimeException("Invalid alliance detected");
      }

      addSequential(new ExtendHoodCommand());
      addSequential(new BoilerLiftInner(1.0, mirrored));
      addParallel(new PlaceGearCommand(), 1.0);
      addParallel(new SpinUpShooterCommand(SmartDashboard.getNumber("RPM1", 0)));
      addSequential(new DriveDistanceCommand(-6.0, 2.0));
      addSequential(new TurnInPlaceCommand(140, mirrored));
      addSequential(new AimCommand(mirrored ? -211 : 220.0, SmartDashboard.getNumber("RPM1", 0), 0.45));
      addSequential(new PrintCommand((String.valueOf(mirrored ? -211 : 211))));
  }
}
