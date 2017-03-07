package org.team2471.frc.steamworks.autonomouscommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;

public class DriveToBoilerLift extends CommandGroup {
  public DriveToBoilerLift(){
    DriverStation driverStation = DriverStation.getInstance();
    DriverStation.Alliance alliance = driverStation.getAlliance();
    boolean mirrored = alliance != DriverStation.Alliance.Blue;        m_path.addPointAndTangent(0.0, 0.0, 0.0, 6.0);
    m_path.addPointAndTangent(6.5, 8.5, 14.0, 4.0);

    addSequential(new DriveToBoilerLiftInner(1.0, mirrored));
    addSequential(new TiltGearIntakeCommand(), 2.0);
  }
}
