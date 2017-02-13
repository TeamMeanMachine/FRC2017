package org.team2471.frc.steamworks;

import org.team2471.frc.steamworks.subsystems.TwinShooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
  public static TwinShooter twinShooter;
  @Override
  public void robotInit() {
    twinShooter = new TwinShooter();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
