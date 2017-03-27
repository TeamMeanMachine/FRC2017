package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.commands.RPMPresetCommand;

public class Shooter extends Subsystem {
  private final CANTalon rightMasterMotor = HardwareMap.TwinShooterMap.masterRight;
  private final CANTalon rightSlaveMotor = HardwareMap.TwinShooterMap.slaveRight;

  private final CANTalon leftMasterMotor = HardwareMap.TwinShooterMap.masterLeft;
  private final CANTalon leftSlaveMotor = HardwareMap.TwinShooterMap.slaveLeft;

  private final CANTalon cycloneMotor = HardwareMap.TwinShooterMap.cycloneMotor;
  private final CANTalon elevatorMotor = HardwareMap.TwinShooterMap.ballFeeder;

  private final Solenoid hoodSolenoid = HardwareMap.TwinShooterMap.hoodSolenoid;
  private int RPMPreset = -1;

  public Shooter() {
    DashboardUtils.putPersistentNumber("Shooter P", 0.05);
    DashboardUtils.putPersistentNumber("Shooter I", 0);
    DashboardUtils.putPersistentNumber("Shooter D", 0.1);
    DashboardUtils.putPersistentNumber("Shooter Left F", 0.072);
    DashboardUtils.putPersistentNumber("Shooter Right F", 0.072);
    DashboardUtils.putPersistentNumber("Shooter Setpoint", 2420);
    DashboardUtils.putPersistentNumber("Shooter Offset", 0.0);

    DashboardUtils.putPersistentNumber("Dist1", 5.5);
    DashboardUtils.putPersistentNumber("Dist2", 8.25);
    DashboardUtils.putPersistentNumber("Dist3", 10.25);
    DashboardUtils.putPersistentNumber("Dist4", 13.5);
    DashboardUtils.putPersistentNumber("Dist5", 9.3);

    DashboardUtils.putPersistentNumber("RPM0", 2420);
    DashboardUtils.putPersistentNumber("RPM1", 2640);
    DashboardUtils.putPersistentNumber("RPM2", 2910);
    DashboardUtils.putPersistentNumber("RPM3", 3160);
    DashboardUtils.putPersistentNumber("RPM4", 4160);
    DashboardUtils.putPersistentNumber("RPM5", 2800);

    DashboardUtils.putPersistentNumber("BoilerMaxFeed", 0.75);

    SmartDashboard.putData("Boiler Preset", new RPMPresetCommand(0));
    SmartDashboard.putData("Hopper", new RPMPresetCommand(1));
    SmartDashboard.putData("1 Bot Back", new RPMPresetCommand(2));
    SmartDashboard.putData("Peg", new RPMPresetCommand(3));
    SmartDashboard.putData("Loading Station", new RPMPresetCommand(4));
    SmartDashboard.putData("Forward 40 KPA", new RPMPresetCommand(5));

    final int codesPerRev = (int) (250 * 1.8); // encoder ticks * gear ratio

    rightMasterMotor.configEncoderCodesPerRev(codesPerRev);
    rightMasterMotor.changeControlMode(TalonControlMode.Speed);
    rightMasterMotor.setProfile(0);
    rightMasterMotor.enableBrakeMode(false);
    rightMasterMotor.setInverted(true);
    rightMasterMotor.reverseOutput(false);
    rightSlaveMotor.changeControlMode(TalonControlMode.Follower);
    rightSlaveMotor.set((double)this.rightMasterMotor.getDeviceID());
    rightSlaveMotor.reverseOutput(false);
    rightSlaveMotor.enableBrakeMode(false);

    //leftMasterMotor.configEncoderCodesPerRev(205);
    leftMasterMotor.configEncoderCodesPerRev(codesPerRev);
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

    // Velocity filtering
    HardwareMap.TwinShooterMap.masterLeft.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_25Ms);  // default 100
    HardwareMap.TwinShooterMap.masterLeft.SetVelocityMeasurementWindow(32);  // default 64
    HardwareMap.TwinShooterMap.masterRight.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_25Ms);
    HardwareMap.TwinShooterMap.masterRight.SetVelocityMeasurementWindow(32);

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

  public boolean onTarget() {
    return rightMasterMotor.getError()<500 && leftMasterMotor.getError()<500;
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

  public int getRPMPreset() {
    return RPMPreset;
  }

  public void setRPMPreset(int RPMPreset) {
    this.RPMPreset = RPMPreset;
  }
}

