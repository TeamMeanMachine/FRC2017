package org.team2471.frc.steamworks.commands;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class ManualClimbCommand extends Command {
  private final CANTalon leftMotor = Robot.drive.getLeftMotor1();
  private final CANTalon rightMotor = Robot.drive.getRightMotor1();

  private boolean intakePressed;
  private boolean automaticIntake;
  private double startDistance;


  public ManualClimbCommand() {
    requires(Robot.drive);
    setInterruptible(false);
  }

  @Override
  protected void initialize() {
    leftMotor.changeControlMode(CANTalon.TalonControlMode.Position);
    rightMotor.changeControlMode(CANTalon.TalonControlMode.Position);
    Robot.drive.enableClimbing();
    automaticIntake = false;

    startDistance = Math.abs(leftMotor.getPosition() + rightMotor.getPosition()) / 2;
  }

  @Override
  protected void execute() {
    double leftDistance = leftMotor.getPosition();
    double rightDistance = rightMotor.getPosition();

    double throttle = IOMap.throttleAxis.get();

    if (throttle > 0.2) {
      Robot.drive.hiGear();
    } else {
      Robot.drive.lowGear();
    }

    throttle *= 16;


    leftMotor.setSetpoint(leftDistance + throttle);
    rightMotor.setSetpoint(rightDistance + throttle);


    double distance = Math.abs(leftDistance + rightDistance) / 2;
    if (automaticIntake) {
      if (distance > 14) {
        Robot.gearIntake.extend();
      } else {
        Robot.gearIntake.retract();
      }
    }

    if (IOMap.climbIntakeOverrideButton.get() && !intakePressed) {
      if (Robot.gearIntake.isExtended()) {
        Robot.gearIntake.retract();
      } else {
        Robot.gearIntake.extend();
      }
      automaticIntake = false;
      intakePressed = true;
    } else if (!IOMap.climbIntakeOverrideButton.get()) {
      intakePressed = false;
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    leftMotor.changeControlMode(CANTalon.TalonControlMode.Voltage);
    rightMotor.changeControlMode(CANTalon.TalonControlMode.Voltage);
  }
}
