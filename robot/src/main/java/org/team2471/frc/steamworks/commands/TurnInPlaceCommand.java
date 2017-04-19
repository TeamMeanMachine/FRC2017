package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.Robot;

public class TurnInPlaceCommand extends PIDCommand {
  private final double angle;

  private double startAngle;

  public TurnInPlaceCommand(double angle, boolean mirrored) {
    super(SmartDashboard.getNumber("Aim P", 0.17), 0, SmartDashboard.getNumber("Aim D", 0.15));
    getPIDController().setOutputRange(-0.5, 0.5);

    if (mirrored) {
      angle = -angle;
    }
    this.angle = angle;
    requires(Robot.drive);
    getPIDController().setAbsoluteTolerance(2);

  }

  @Override
  protected void initialize() {
    setSetpoint(returnPIDInput() + angle);
  }

  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget() || isTimedOut();
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
