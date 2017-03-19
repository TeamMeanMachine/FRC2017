package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;

public class Shooter extends Subsystem {
  private final CANTalon rightMasterMotor = HardwareMap.TwinShooterMap.masterRight;
  private final CANTalon rightSlaveMotor = HardwareMap.TwinShooterMap.slaveRight;

  private final CANTalon leftMasterMotor = HardwareMap.TwinShooterMap.masterLeft;
  private final CANTalon leftSlaveMotor = HardwareMap.TwinShooterMap.slaveLeft;

  private final CANTalon cycloneMotor = HardwareMap.TwinShooterMap.cycloneMotor;
  private final CANTalon elevatorMotor = HardwareMap.TwinShooterMap.ballFeeder;

  private final Solenoid hoodSolenoid = HardwareMap.TwinShooterMap.hoodSolenoid;

  public Shooter() {
    DashboardUtils.putPersistantNumber("Shooter P", 0.05);
    DashboardUtils.putPersistantNumber("Shooter I", 0);
    DashboardUtils.putPersistantNumber("Shooter D", 1.0);
    DashboardUtils.putPersistantNumber("Shooter Left F", 0.07);
    DashboardUtils.putPersistantNumber("Shooter Right F", 0.07);
    DashboardUtils.putPersistantNumber("Shooter Setpoint", 6000);

    //rightMasterMotor.configEncoderCodesPerRev(1445);
    rightMasterMotor.configEncoderCodesPerRev(205);
    rightMasterMotor.changeControlMode(TalonControlMode.Speed);
    rightMasterMotor.setProfile(0);
    rightMasterMotor.enableBrakeMode(false);
    rightMasterMotor.setInverted(true);
    rightMasterMotor.reverseOutput(false);
    rightSlaveMotor.changeControlMode(TalonControlMode.Follower);
    rightSlaveMotor.set((double)this.rightMasterMotor.getDeviceID());
    rightSlaveMotor.reverseOutput(false);
    rightSlaveMotor.enableBrakeMode(false);

    //leftMasterMotor.configEncoderCodesPerRev(1445);
    leftMasterMotor.configEncoderCodesPerRev(205);
    leftMasterMotor.changeControlMode(TalonControlMode.Speed);
    leftMasterMotor.setProfile(0);
    leftMasterMotor.enableBrakeMode(false);
    leftMasterMotor.setInverted(false);
    leftMasterMotor.reverseOutput(false);
    leftSlaveMotor.changeControlMode(TalonControlMode.Follower);
    leftSlaveMotor.set(this.leftMasterMotor.getDeviceID());
    leftSlaveMotor.reverseOutput(false);
    leftSlaveMotor.enableBrakeMode(false);

    // set ramp rates
    leftMasterMotor.setVoltageRampRate(32);
    leftSlaveMotor.setVoltageRampRate(32);
    rightMasterMotor.setVoltageRampRate(32);
    rightSlaveMotor.setVoltageRampRate(32);

    cycloneMotor.setInverted(false);
    elevatorMotor.setInverted(true);

    if (Robot.COMPETITION) {
      leftMasterMotor.reverseSensor(false);
      rightMasterMotor.reverseSensor(false);
    }
    else {
      leftMasterMotor.reverseSensor(true);  // the weird one
      rightMasterMotor.reverseSensor(false);
    }
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
    setIntake(0.0, 0.0);
  }

  public void setIntake(double cycloneSpeed, double elevatorSpeed) {
    cycloneMotor.set(cycloneSpeed);
    elevatorMotor.set(elevatorSpeed);
  }

  public void extendHood() {
    System.out.println("Extend");
    hoodSolenoid.set(true);
  }

  public void retractHood() {
    System.out.println("Retract");
    hoodSolenoid.set(false);
  }

  public boolean isHoodUp(){
    return hoodSolenoid.get();
  }

  protected void initDefaultCommand() {
  }
}

