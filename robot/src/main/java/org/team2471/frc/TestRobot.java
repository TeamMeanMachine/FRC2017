package org.team2471.frc;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Use this class when you want to test individual hardware pieces without having to comment out a ton of stuff
public class TestRobot extends IterativeRobot {
  private static CANTalon[] talons;
  public static Solenoid solenoid;

  private static int lastId = 0;

  @Override
  public void robotInit() {
    talons = new CANTalon[16];
    for(int i=0; i<16; i++) {
      talons[i] = new CANTalon(i);
    }

    talons[2].reverseOutput(true);
    talons[2].setInverted(true);

    solenoid = new Solenoid(0);
    SmartDashboard.putNumber("Test Number", 0);
    SmartDashboard.putNumber("Test Power", 0);
  }

  @Override
  public void teleopInit() {
    solenoid.set(true);
  }

  @Override
  public void robotPeriodic() {
    int current = (int) SmartDashboard.getNumber("Test Number", 0);
    if(current != lastId) {
      talons[lastId].set(0);
    }
    talons[current].set(SmartDashboard.getNumber("Test Power", 0));
    lastId = current;
  }

  @Override
  public void testPeriodic() {
  }
}
