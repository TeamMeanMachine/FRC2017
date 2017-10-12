package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

public class TurnInPlaceCommand extends PIDCommand {
  private final double angle;

  private final Timer timer = new Timer();

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
    timer.start();
    setSetpoint(returnPIDInput() + angle);
  }

  @Override
  protected boolean isFinished() {
    return Math.abs( getPIDController().getSetpoint() - returnPIDInput()) < 2.0 && timer.get() > 0.15 || isTimedOut();
  }

  @Override
  protected void end() {
    timer.stop();
  }

  @Override
  protected double returnPIDInput() {
    return HardwareMap.gyro.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.arcade(0, output);
  }
}
