package org.team2471.frc.steamworks.defaultcommands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;

import static org.team2471.frc.steamworks.Robot.twinShooter;

public class TwinShooterDefaultCommand extends Command{
  public TwinShooterDefaultCommand() {
    requires(twinShooter);
    SmartDashboard.putNumber("SHOOTER_ACCELERATION", 0);
    SmartDashboard.putNumber("SHOOTER_SETPOINT", 0);

    DashboardUtils.putPersistantNumber("SHOOTER_P", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_I", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_D", 0);
    DashboardUtils.putPersistantNumber("SHOOTER_F", 0);

  }

  private double startTime;

  private double previousVelocity = 0;
  private double previousVelocityTimestamp = Timer.getFPGATimestamp();
  private double acceleration = 0;

  @Override
  protected void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  protected void execute() {
    twinShooter.setRPM(SmartDashboard.getNumber("SHOOTER_SETPOINT", 0));

    double currentVelocity = twinShooter.getSpeed();
    double currentVelocityTimestamp = Timer.getFPGATimestamp();
    // in rpm/s
    acceleration = (currentVelocity - previousVelocity) / (currentVelocityTimestamp - previousVelocityTimestamp);
    SmartDashboard.putNumber("SHOOTER_ACCELERATION", acceleration);
    previousVelocity = currentVelocity;
    previousVelocityTimestamp = currentVelocityTimestamp;

    twinShooter.setPIDF(
            SmartDashboard.getNumber("SHOOTER_P", 0),
            SmartDashboard.getNumber("SHOOTER_I", 0),
            SmartDashboard.getNumber("SHOOTER_D", 0),
            SmartDashboard.getNumber("SHOOTER_F", 0)
    );
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
