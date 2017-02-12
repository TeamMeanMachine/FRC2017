package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.Drive;
import org.team2471.frc.steamworks.subsystems.GearIntake;

public class Robot extends IterativeRobot {
  public static Drive drive;

  public static GearIntake gearIntake;

  @Override
  public void robotInit() {
    drive = new Drive();
    gearIntake = new GearIntake();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
