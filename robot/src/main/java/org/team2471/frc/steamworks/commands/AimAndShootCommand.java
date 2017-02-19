package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.comm.VisionData;

public class AimAndShootCommand extends PIDCommand {

  private final PIDController pidController = getPIDController();

  private boolean targetFound = false;
  private double lastError = 1.0;

  public AimAndShootCommand() {
    super(1.0/45.0, 0, 1.0/45.0);
    requires(Robot.drive);
    requires(Robot.twinShooter);
    requires(Robot.gearIntake);

    pidController.setAbsoluteTolerance(0.1);
    pidController.setToleranceBuffer(10);
  }

  @Override
  protected void initialize() {
    Robot.twinShooter.enable();
    pidController.enable();
  }


  @Override
  public void execute() {
    Robot.twinShooter.setRPM(SmartDashboard.getNumber("Shooter Speed", 0));

    // in rpm/s
    SmartDashboard.putNumber("Shooter Left Speed", Robot.twinShooter.getLeftSpeed());
    SmartDashboard.putNumber("Shooter Left Error", Robot.twinShooter.getLeftError());
    SmartDashboard.putNumber("Shooter Left Master Voltage", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Left Slave Voltage", HardwareMap.TwinShooterMap.slaveLeft.getOutputVoltage() / 12);

    SmartDashboard.putNumber("Shooter Right Speed", Robot.twinShooter.getRightSpeed());
    SmartDashboard.putNumber("Shooter Right Error", Robot.twinShooter.getRightError());
    SmartDashboard.putNumber("Shooter Right Master Voltage", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Right Slave Voltage", HardwareMap.TwinShooterMap.slaveRight.getOutputVoltage() / 12);

    Robot.twinShooter.setPIDF(
        SmartDashboard.getNumber("Shooter P", 0),
        SmartDashboard.getNumber("Shooter I", 0),
        SmartDashboard.getNumber("Shooter D", 0),
        SmartDashboard.getNumber("Shooter F", 0)
    );

    Robot.twinShooter.setRPM(SmartDashboard.getNumber("Shooter Setpoint", 5000));

    SmartDashboard.putNumber("Shooter Left Voltage", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Right Voltage", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);


    boolean shoot = SmartDashboard.getBoolean("Auto Aim", false) ?
        pidController.onTarget() && targetFound : IOMap.shootButton.get();

    if(IOMap.hoodDPad.isUp()) {
      Robot.twinShooter.extendHood();
    } else if(IOMap.hoodDPad.isDown()) {
      Robot.twinShooter.retractHood();
    }

    Robot.gearIntake.extend();

    if (shoot) {
      Robot.twinShooter.enableFeed();
    } else {
      Robot.twinShooter.disableFeed();
    }
  }



  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.twinShooter.disable();
    Robot.gearIntake.retract();
    pidController.disable();
  }

  @Override
  protected double returnPIDInput() {
    if (SmartDashboard.getBoolean("Auto Aim", false)) {
      VisionData boilerData = Robot.coProcessor.getBoilerData();
      if(boilerData.isPresent()) {
        targetFound = true;
        double distance = boilerData.getDistance(); // TODO: do stuff with distance
        lastError = boilerData.getError();
        return lastError;
      } else {
        targetFound = false;
        return lastError;
      }
    } else {
      targetFound = true;
      lastError = IOMap.aimAxis.get() * 45;
      return lastError;
    }
  }

  @Override
  protected void usePIDOutput(double output) {
    if(!targetFound) {
      output = 0;
    }

    Robot.drive.turnInPlace(output);
  }
}