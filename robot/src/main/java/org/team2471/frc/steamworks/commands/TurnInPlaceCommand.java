package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.Robot;

public class TurnInPlaceCommand extends PIDCommand {
  private final double angle;

  public TurnInPlaceCommand(double angle, boolean mirrored) {
    super(SmartDashboard.getNumber("Aim P", 0.17), 0, SmartDashboard.getNumber("Aim D", 0.15));
    requires(Robot.drive);
    getPIDController().setOutputRange(-0.5, 0.5);

    if (mirrored) {
      angle = -angle;
    }
    this.angle = angle;

  }

  @Override
  protected void initialize() {
    setSetpoint(returnPIDInput() + angle);
  }

  @Override
  protected boolean isFinished() {
    return getPIDController().getError() < 2.0 || isTimedOut();
  }

  @Override
  protected double returnPIDInput() {
    return Robot.drive.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.arcade(0, output);
  }
}
