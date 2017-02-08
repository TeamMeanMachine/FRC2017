package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.Drive;
import org.team2471.frc.steamworks.subsystems.TwinShooter;

public class Robot extends IterativeRobot {
  public static Drive drive;
  public static TwinShooter twinShooter;
  public static CoProcessor coProcessor;

  @Override
  public void robotInit() {
    drive = new Drive();
    twinShooter = new TwinShooter();
    coProcessor = new CoProcessor();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

}
