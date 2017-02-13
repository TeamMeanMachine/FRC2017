package org.team2471.frc.steamworks;

import org.team2471.frc.steamworks.subsystems.TwinShooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.Drive;
import org.team2471.frc.steamworks.subsystems.GearIntake;
import org.team2471.frc.steamworks.subsystems.FuelIntake;

public class Robot extends IterativeRobot {
  public static Drive drive;

  public static GearIntake gearIntake;

  public static FuelIntake fuelIntake;

  public static TwinShooter twinShooter;
  @Override
  public void robotInit() {
    twinShooter = new TwinShooter();
    drive = new Drive();
    gearIntake = new GearIntake();
    fuelIntake = new FuelIntake();
    IOMap.init();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
