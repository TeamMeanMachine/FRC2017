package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
  private final CANTalon rightMasterMotor = new CANTalon(10);
  private final CANTalon rightSlaveMotor = new CANTalon(11);

  private final CANTalon leftMasterMotor = new CANTalon(5);
  private final CANTalon leftSlaveMotor = new CANTalon(4);

  private final CANTalon cycloneMotor = new CANTalon(12);
  private final CANTalon elevatorMotor = new CANTalon(6);

  public Shooter() {
    rightMasterMotor.configEncoderCodesPerRev(205);
    rightMasterMotor.changeControlMode(TalonControlMode.Speed);
    rightMasterMotor.setProfile(0);
    rightMasterMotor.reverseSensor(true);
    rightMasterMotor.enableBrakeMode(false);
    rightSlaveMotor.changeControlMode(TalonControlMode.Follower);
    rightSlaveMotor.set((double)this.rightMasterMotor.getDeviceID());
    rightSlaveMotor.reverseOutput(false);
    rightSlaveMotor.enableBrakeMode(false);

    // set ramp rate
    leftMasterMotor.setVoltageRampRate(6);
    leftSlaveMotor.setVoltageRampRate(6);
    rightMasterMotor.setVoltageRampRate(6);
    rightSlaveMotor.setVoltageRampRate(6);


    leftMasterMotor.configEncoderCodesPerRev(205);
    leftMasterMotor.changeControlMode(TalonControlMode.Speed);
    leftMasterMotor.setProfile(0);
    leftMasterMotor.reverseSensor(true);
    leftMasterMotor.enableBrakeMode(false);
    leftSlaveMotor.changeControlMode(TalonControlMode.Follower);
    leftSlaveMotor.set(this.leftMasterMotor.getDeviceID());
    leftMasterMotor.reverseOutput(false);
    leftMasterMotor.reverseSensor(true);

    leftSlaveMotor.reverseOutput(false);
    leftSlaveMotor.enableBrakeMode(false);

    rightMasterMotor.reverseOutput(false);
    rightMasterMotor.reverseSensor(true);
    rightMasterMotor.setInverted(true);


    cycloneMotor.setInverted(true);
    elevatorMotor.setInverted(false);
    rightMasterMotor.getClass();
  }

  public void setPID(double p, double i, double d, double leftF, double rightF) {
    leftMasterMotor.setPID(p, i, d);
    leftMasterMotor.setF(leftF);

    rightMasterMotor.setPID(p, i, d);
    rightMasterMotor.setF(rightF);
    SmartDashboard.putNumber("Shooter Output Diff", (this.rightMasterMotor.getOutputVoltage() - this.rightSlaveMotor.getOutputVoltage()) / 12.0);
  }

  public void enable() {
    rightMasterMotor.enable();
    leftMasterMotor.enable();
  }

  public void disable() {
    rightMasterMotor.disable();
    leftMasterMotor.disable();
  }

  public void setRawPower(double power) {
    rightMasterMotor.changeControlMode(TalonControlMode.PercentVbus);
    leftMasterMotor.changeControlMode(TalonControlMode.PercentVbus);
    rightMasterMotor.set(power);
    leftMasterMotor.set(power);
  }

  public void setSetpoint(double setpoint) {
    rightMasterMotor.changeControlMode(TalonControlMode.Speed);
    leftMasterMotor.changeControlMode(TalonControlMode.Speed);
    rightMasterMotor.set(setpoint);
    leftMasterMotor.set(setpoint);
  }

  public double getLeftError() {
    return this.leftMasterMotor.getError();
  }

  public double getRightError() {
    return this.rightMasterMotor.getError();
  }

  public double getLeftOutput() {
    return leftMasterMotor.getOutputVoltage() / 12.0 + Math.random() / 10000.0;
  }

  public double getRightOutput() {
    return rightMasterMotor.getOutputVoltage() / 12.0 + Math.random() / 10000.0;
  }


  public double getLeftSpeed() {
    return this.leftMasterMotor.getSpeed();
  }

  public double getRightSpeed() {
    return this.rightMasterMotor.getSpeed();
  }

  public void reset() {
    rightMasterMotor.set(0.0);
    leftMasterMotor.set(0.0);
    setIntake(0.0);
  }

  public void setIntake(double power) {
    cycloneMotor.set(power);
    elevatorMotor.set(power);
  }

  protected void initDefaultCommand() {
  }
}

