package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class DriveDefaultCommand extends Command {

/*
  double leftStartPos;
  double rightStartPos;
*/

  public DriveDefaultCommand() {
    requires(Robot.drive);
  }

  @Override
  protected void initialize() {
/*
    HardwareMap.DriveMap.leftMotor1.changeControlMode(CANTalon.TalonControlMode.Position);
    HardwareMap.DriveMap.rightMotor1.changeControlMode(CANTalon.TalonControlMode.Position);

    leftStartPos = HardwareMap.DriveMap.leftMotor1.getPosition();
    rightStartPos = HardwareMap.DriveMap.rightMotor1.getPosition();
*/
  }

  @Override
  protected void execute() {
    Robot.drive.drive(IOMap.throttleAxis.get(), IOMap.turnAxis.get(), IOMap.leftAxis.get(), IOMap.rightAxis.get());
    SmartDashboard.putNumber("Drive Distance", Robot.drive.getDistance());

/*
    HardwareMap.DriveMap.leftMotor1.setSetpoint( leftStartPos + IOMap.throttleAxis.get());
    HardwareMap.DriveMap.rightMotor1.setSetpoint( rightStartPos + IOMap.throttleAxis.get());

    CANController leftMotor1 = HardwareMap.DriveMap.rightMotor1;
    System.out.println(
            "SetPoint: " + leftMotor1.getSetpoint() +
            " Position: " + leftMotor1.getPosition() +
            " Error: " + leftMotor1.getError() +
            " Output: " + (leftMotor1.getOutputVoltage() / 12.0) +
            " PID: " + leftMotor1.getP() + " " + leftMotor1.getI() + " " + leftMotor1.getD() );
*/
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
