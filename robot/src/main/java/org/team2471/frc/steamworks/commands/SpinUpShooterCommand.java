package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.Robot;

public class SpinUpShooterCommand extends Command {
  private final double setpoint;

  public SpinUpShooterCommand(double setpoint) {
    this.setpoint = setpoint;
    requires(Robot.shooter);
  }

  @Override
  protected void initialize() {
    Robot.shooter.setRampRate(32);
    Robot.shooter.enable();
  }

  @Override
  protected void execute() {
    Robot.shooter.setSetpoint(setpoint);
  }

  @Override
  protected void end() {
    Robot.shooter.setRampRate(0);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}
