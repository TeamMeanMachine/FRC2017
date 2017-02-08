package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.GearIntake;

public class Robot extends IterativeRobot {

  public static GearIntake gearIntake;

  @Override
  public void robotInit() {
    gearIntake = new GearIntake();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
