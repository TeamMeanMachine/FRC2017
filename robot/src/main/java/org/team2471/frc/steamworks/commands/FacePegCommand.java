package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.util.MeanMath;

public class FacePegCommand extends PIDCommand {
  public FacePegCommand() {
    super(1.0/45.0, 0, 1.0/45.0);
    requires(Robot.drive);
  }

  private double targetAngle = 0;

  @Override
  protected void initialize() {
    double angle = Robot.drive.getAngle();
    double leftDelta = (angle - 45) % 360;
    double centerDelta = angle % 360;
    double rightDelta = (angle + 45) % 360;
    double closestDelta = MeanMath.min(leftDelta, centerDelta, rightDelta);
    targetAngle = angle + closestDelta;
  }

  @Override
  protected void execute() {
    setSetpoint(targetAngle);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected double returnPIDInput() {
    return Robot.drive.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.turnInPlace(output);
  }
}
