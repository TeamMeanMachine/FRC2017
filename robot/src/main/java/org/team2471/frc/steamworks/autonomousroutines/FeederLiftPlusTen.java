package org.team2471.frc.steamworks.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.PlaceGearCommand;

public class FeederLiftPlusTen extends CommandGroup {
  public FeederLiftPlusTen() {
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance == DriverStation.Alliance.Red;

    addSequential(new FeederLiftInner(1.0, mirrored));
    addParallel(new PlaceGearCommand(), 2.0);
    addSequential(new FaceBoilerFromFeederLift(1.0, mirrored));
    addSequential(new FaceBoilerFromFeederLift(1.0, mirrored));
//    addSequential(new AimCommand(-125, SmartDashboard.getNumber("RPM1", 2471), 0.5));

    //addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
