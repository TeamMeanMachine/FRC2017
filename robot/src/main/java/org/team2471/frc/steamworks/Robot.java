package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.LEDController;

public class Robot extends IterativeRobot {
  public static LEDController ledController;

  @Override
  public void robotInit() {
    ledController = new LEDController();
    IOMap.init();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }
}
