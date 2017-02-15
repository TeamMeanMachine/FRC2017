package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import org.team2471.frc.steamworks.HardwareMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.defaultcommands.TwinShooterDefaultCommand;

import static org.team2471.frc.steamworks.HardwareMap.TwinShooterMap.cycloneMotor;

public class TwinShooter extends Subsystem {
  private CANTalon masterLeft = HardwareMap.TwinShooterMap.masterLeft;
  private CANTalon slaveLeft = HardwareMap.TwinShooterMap.slaveLeft;
  private CANTalon masterRight = HardwareMap.TwinShooterMap.masterRight;
  private CANTalon slaveRight = HardwareMap.TwinShooterMap.slaveRight;
  private CANTalon ballFeeder = HardwareMap.TwinShooterMap.ballFeeder;
  private Solenoid hood = HardwareMap.TwinShooterMap.hoodSolenoid;


  public TwinShooter() {
    masterLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
    slaveLeft.changeControlMode(CANTalon.TalonControlMode.Follower);

    masterRight.changeControlMode(CANTalon.TalonControlMode.Speed);
    masterRight.setInverted(true);
    slaveRight.changeControlMode(CANTalon.TalonControlMode.Follower);

    ballFeeder.setInverted(true);

    // make sure the motors are in coast mode
    masterLeft.enableBrakeMode(false);
    masterLeft.reverseSensor(true);
    slaveLeft.enableBrakeMode(false);
    masterRight.enableBrakeMode(false);
    masterRight.reverseSensor(true);
    slaveRight.enableBrakeMode(false);
  }

  public double getLeftSpeed() {
    return masterLeft.getSpeed();
  }

  public double getLeftError() {
    return masterLeft.getError();
  }

  public double getRightSpeed() {
    return masterRight.getSpeed();
  }

  public double getRightError() {
    return masterRight.getError();
  }

  public void setPIDF(double p, double i, double d, double f) {
    masterLeft.setPID(p, i, d);
    masterLeft.setF(f);
    masterRight.setPID(p, i, d);
    masterRight.setF(f);
  }

  /**
   * Enable... This really needs no explanation.
   */
  public void enable() {
    masterLeft.enable();
    masterRight.enable();
  }

  /**
   * Disable...
   */
  public void disable() {
    masterLeft.disable();
    masterRight.disable();
  }

  /**
   * Runs master and slave forward on both
   */
  public void setRPM(double rpm) {
    masterLeft.setSetpoint(rpm);
    masterRight.setSetpoint(rpm);
  }

  /**
   * Runs the ball feeder thingymabobber
   */
  public void disableFeed() {
    ballFeeder.set(0);
    cycloneMotor.set(0);
  }

  public void enableFeed() {
    ballFeeder.set(1);
    cycloneMotor.set(1);
  }

  public void extendHood() {
    hood.set(true);
  }

  public void retractHood() {
    hood.set(false);
  }


  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TwinShooterDefaultCommand());
  }
}

