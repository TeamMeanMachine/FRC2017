package org.team2471.frc.steamworks;

import org.team2471.frc.steamworks.subsystems.TwinShooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
  public static TwinShooter twinshooter;
  @Override
  public void robotInit() {
    twinshooter = new TwinShooter();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
