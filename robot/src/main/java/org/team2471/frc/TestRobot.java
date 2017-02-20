package org.team2471.frc;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Use this class when you want to test individual hardware pieces without having to comment out a ton of stuff
public class TestRobot extends IterativeRobot {
  private static CANTalon[] talons;

  @Override
  public void robotInit() {
    talons = new CANTalon[] {
//        new CANTalon(4),
        new CANTalon(5),
//        new CANTalon(10),
//        new CANTalon(11)
    };
  }

  @Override
  public void robotPeriodic() {
    for (CANTalon talon : talons) {
      talon.set(0.4);
    }
  }
}
