package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.comm.VisionData;
import org.team2471.frc.steamworks.subsystems.GearIntake;
import org.team2471.frc.steamworks.subsystems.TwinShooter;

public class AimAndShootCommand extends PIDCommand {

  private final PIDController pidController = getPIDController();
  private Timer timer1 = new Timer();
  private Timer timer2 = new Timer();

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
    timer1.start();
    Robot.twinShooter.enable();
    pidController.enable();
  }


  @Override
  public void execute() {
    Robot.twinShooter.setRPM((int) SmartDashboard.getNumber("Shooter Setpoint", 5000));

    // in rpm/s
    SmartDashboard.putNumber("Shooter Left Speed", Robot.twinShooter.getLeftSpeed());
    SmartDashboard.putNumber("Shooter Left Error", Robot.twinShooter.getLeftError());
    SmartDashboard.putNumber("Shooter Left Master Voltage", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Left Slave Voltage", HardwareMap.TwinShooterMap.slaveLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Left Setpoint", HardwareMap.TwinShooterMap.masterLeft.getSetpoint());

    SmartDashboard.putNumber("Shooter Right Speed", Robot.twinShooter.getRightSpeed());
    SmartDashboard.putNumber("Shooter Right Error", Robot.twinShooter.getRightError());
    SmartDashboard.putNumber("Shooter Right Master Voltage", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Right Slave Voltage", HardwareMap.TwinShooterMap.slaveRight.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Right Setpoint", HardwareMap.TwinShooterMap.masterRight.getSetpoint());

    SmartDashboard.putString("Shooter Errors", Robot.twinShooter.getLeftError() + ":" + Robot.twinShooter.getRightError());

    Robot.twinShooter.setPIDF(
        SmartDashboard.getNumber("Shooter P", 0),
        SmartDashboard.getNumber("Shooter I", 0),
        SmartDashboard.getNumber("Shooter D", 0),
        SmartDashboard.getNumber("Shooter I Zone", 0),
        SmartDashboard.getNumber("Shooter Left F", 0),
        SmartDashboard.getNumber("Shooter Right F", 0)
    );

    SmartDashboard.putNumber("Shooter Left Voltage", HardwareMap.TwinShooterMap.masterLeft.getOutputVoltage() / 12);
    SmartDashboard.putNumber("Shooter Right Voltage", HardwareMap.TwinShooterMap.masterRight.getOutputVoltage() / 12);

    SmartDashboard.putString("Shooter Amps", HardwareMap.pdp.getCurrent(HardwareMap.TwinShooterMap.masterLeft.getDeviceID()) + ":" +
                                                  HardwareMap.pdp.getCurrent(HardwareMap.TwinShooterMap.masterRight.getDeviceID()));


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

    if (timer1.get() > 2) {
      Robot.gearIntake.extend();
      timer2.start();
      timer1.stop();
    }
    if (timer2.get() > .5) {
      Robot.gearIntake.retract();
      timer1.start();
      timer2.stop();
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
    timer1.stop();
    timer2.stop();
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
      lastError = IOMap.aimAxis.get() * 15;
      return lastError;
    }
  }

  @Override
  protected void usePIDOutput(double output) {
    if(!targetFound) {
      output = 0;
    }

    Robot.drive.turnInPlace(-output); // inverted for some reason
  }
}