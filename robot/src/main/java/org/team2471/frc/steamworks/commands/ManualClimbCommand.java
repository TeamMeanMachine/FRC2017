package org.team2471.frc.steamworks.commands;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class ManualClimbCommand extends Command {
  private final CANTalon leftMotor = Robot.drive.getLeftMotor1();
  private final CANTalon rightMotor = Robot.drive.getRightMotor1();

  private boolean intakePressed;


  public ManualClimbCommand() {
    requires(Robot.drive);
    setInterruptible(false);
  }

  @Override
  protected void initialize() {
    leftMotor.changeControlMode(CANTalon.TalonControlMode.Position);
    rightMotor.changeControlMode(CANTalon.TalonControlMode.Position);
    Robot.drive.enableClimbing();
  }

  @Override
  protected void execute() {
    double leftDistance = leftMotor.getPosition();
    double rightDistance = rightMotor.getPosition();

    double throttle = IOMap.throttleAxis.get();

    Robot.drive.hiGear();

    throttle *= 12;
    SmartDashboard.putNumber("Climb Throttle", throttle);


    leftMotor.setSetpoint(leftDistance + throttle);
    rightMotor.setSetpoint(rightDistance + throttle);

    if (IOMap.climbIntakeOverrideButton.get() && !intakePressed) {
      if (Robot.gearIntake.isExtended()) {
        Robot.gearIntake.retract();
      } else {
        Robot.gearIntake.extend();
      }
      intakePressed = true;
    } else if (!IOMap.climbIntakeOverrideButton.get()) {
      intakePressed = false;
    }
    SmartDashboard.putNumber("Climb Voltage", leftMotor.getOutputVoltage() / 12.0);
  }

  @Override
  protected boolean isFinished() {
    return IOMap.exitClimbButton.get();
  }

  @Override
  protected void end() {
    Robot.drive.disableClimbing();
    Robot.gearIntake.retract();
    leftMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    rightMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
  }
}
