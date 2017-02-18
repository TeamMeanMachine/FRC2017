package org.team2471.frc.steamworks.defaultcommands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;

import static org.team2471.frc.steamworks.Robot.twinShooter;

public class TwinShooterDefaultCommand extends Command {
  public TwinShooterDefaultCommand() {
    requires(twinShooter);
    SmartDashboard.putNumber("SHOOTER_SETPOINT", 0);
  }


  @Override
  protected void initialize() {
    twinShooter.enable();
  }

  @Override
  protected void execute() {

    double dpadPosition = IOMap.hoodDPad.get();
    if(dpadPosition == 315 || dpadPosition == 0 || dpadPosition == 45) {
      twinShooter.extendHood();
    } else if(dpadPosition >= 135 && dpadPosition <= 225) {
      twinShooter.retractHood();
    }

    if (IOMap.shootButton.get()) {
      twinShooter.enableFeed();
    } else {
      twinShooter.disableFeed();
    }
  }

  @Override
  protected void end() {
    twinShooter.disable();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
