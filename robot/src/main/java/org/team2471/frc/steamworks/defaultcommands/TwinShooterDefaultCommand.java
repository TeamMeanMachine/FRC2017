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
    SmartDashboard.putNumber("SHOOTER_ACCELERATION", 0);
    SmartDashboard.putNumber("SHOOTER_SETPOINT", 0);

    DashboardUtils.putPersistantNumber("SHOOTER_P", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_I", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_D", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_F", 0);

  }


  @Override
  protected void initialize() {
    twinShooter.enable();
  }

  @Override
  protected void execute() {
    twinShooter.setRPM(SmartDashboard.getNumber("SHOOTER_SETPOINT", 0));

    // in rpm/s
    SmartDashboard.putNumber("SHOOTER_LEFT_SPEED", twinShooter.getLeftSpeed());
    SmartDashboard.putNumber("SHOOTER_LEFT_ERROR", twinShooter.getLeftError());

    SmartDashboard.putNumber("SHOOTER_RIGHT_SPEED", twinShooter.getRightSpeed());
    SmartDashboard.putNumber("SHOOTER_RIGHT_ERROR", twinShooter.getRightError());

    twinShooter.setPIDF(
        SmartDashboard.getNumber("SHOOTER_P", 0),
        SmartDashboard.getNumber("SHOOTER_I", 0),
        SmartDashboard.getNumber("SHOOTER_D", 0),
        SmartDashboard.getNumber("SHOOTER_F", 0)
    );

    SmartDashboard.putNumber("SHOOTER_LEFT_VOLTAGE", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("SHOOTER_RIGHT_VOLTAGE", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);

    double dpadPosition = IOMap.hoodDPad.get();
    if(dpadPosition == 0) {
      twinShooter.extendHood();
    } else if(dpadPosition == 180) {
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
    System.out.println("Disable");
    twinShooter.disable();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
