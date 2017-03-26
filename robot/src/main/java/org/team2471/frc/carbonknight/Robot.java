package org.team2471.frc.carbonknight;

import com.team254.frc2016.CheesyDriveHelper;
import com.team254.lib.util.DriveSignal;

import org.team2471.frc.lib.io.Controller;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import org.team2471.frc.carbonknight.LEDController.*;


public class Robot extends IterativeRobot {
  private static Joystick driverController = new Joystick(0);

  private CheesyDriveHelper cheesyDriveHelper;

  private Talon leftMotorA;
  private Talon leftMotorB;
  private Talon rightMotorA;
  private Talon rightMotorB;

  private LEDController ledController;



  public void Drive() {
  }
  @Override
  public void robotInit() {

    leftMotorA = new Talon(1);
    leftMotorB = new Talon(2);
    rightMotorA = new Talon(3);
    rightMotorB = new Talon(4);

    ledController = new LEDController();
  }

  @Override
  public void robotPeriodic() {

    double throttle = driverController.getRawAxis(1);
    double turn = driverController.getRawAxis(4);
    double turnLeft = driverController.getRawAxis(2);
    double turnRight = driverController.getRawAxis(3);
    boolean gearIn = driverController.getRawButton(1);
    boolean fuelIn = driverController.getRawButton(2);
    boolean defualt = driverController.getRawButton(3);
    boolean shoot = driverController.getRawButton(4);

    if (gearIn) {
      ledController.write("LoadGear");
    }else  if (fuelIn){
      ledController.write("LoadFuel");
    }else if (shoot){
      ledController.write("Aim");
    }else{
      ledController.write("Default");
    }
    DriveSignal signal = cheesyDriveHelper.cheesyDrive(throttle, turn, false);


    double leftPower = signal.leftMotor - turnLeft + turnRight;
    double rightPower = signal.rightMotor - turnRight + turnLeft;

    double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    if(maxPower > 1) {
      leftPower /= maxPower;
      rightPower /= maxPower;
    }

    leftMotorA.set(leftPower);
    leftMotorB.set(leftPower);
    rightMotorA.set(rightPower);
    rightMotorB.set(rightPower);

  }

  @Override
  public void disabledPeriodic() {
    leftMotorA.set(0);
    leftMotorB.set(0);
    rightMotorA.set(0);
    rightMotorB.set(0);


  }

}

