package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class DriveDefaultCommand extends Command {

  public DriveDefaultCommand() {
    requires(Robot.drive);
  }

  @Override
  protected void initialize() {
    Robot.drive.disableClimbing();
  }

  @Override
  protected void execute() {
    Robot.drive.drive(IOMap.throttleAxis.get(), IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get());
    SmartDashboard.putNumber("Drive Speed", Robot.drive.getSpeed());

    double left1Speed = HardwareMap.DriveMap.leftMotor1.getOutputVoltage() / 12;
    double left2Speed = HardwareMap.DriveMap.leftMotor2.getOutputVoltage() / 12;
    double left3Speed = HardwareMap.DriveMap.leftMotor3.getOutputVoltage() / 12;
    double right1Speed = HardwareMap.DriveMap.rightMotor1.getOutputVoltage() / 12;
    double right2Speed = HardwareMap.DriveMap.rightMotor2.getOutputVoltage() / 12;
    double right3Speed = HardwareMap.DriveMap.rightMotor3.getOutputVoltage() / 12;
    SmartDashboard.putString("Left Outputs", left1Speed + ":" + left2Speed + ":" + left3Speed);
    SmartDashboard.putString("Right Outputs", right1Speed + ":" + right2Speed + ":" + right3Speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
