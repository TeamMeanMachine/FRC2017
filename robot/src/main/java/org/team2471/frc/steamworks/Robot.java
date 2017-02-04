package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.Drive;

public class Robot extends IterativeRobot {
  public static Drive drive;
  @Override
  public void robotInit() {
    drive = new Drive();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
