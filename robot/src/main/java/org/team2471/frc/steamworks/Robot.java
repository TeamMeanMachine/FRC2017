package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
  Preferences prefs;
  private CANTalon motor0;
  private CANTalon motor1;
  //private PIDController motor0Controller;
  //private double acc;

  @Override
  public void robotInit() {
    prefs = Preferences.getInstance();

    // motor 0 - master
    motor0 = new CANTalon(2);
    motor0.configEncoderCodesPerRev(820 / 4);
    motor0.changeControlMode(CANTalon.TalonControlMode.Speed);
    //motor0.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    motor0.configNominalOutputVoltage(+0.0f, -0.0f);
    motor0.configPeakOutputVoltage(+12.0f, -12.0f);
    motor0.setProfile(0);
    motor0.reverseSensor(true);

    // motor 1 - follower
    motor1 = new CANTalon(7);
    motor1.changeControlMode(CANTalon.TalonControlMode.Follower);
    motor1.set(motor0.getDeviceID());
    motor1.setInverted(false);
    motor1.reverseOutput(true);
/*
    motor0Controller = new PIDController(-0.000005, 0, -0.0001, new PIDSource() {
      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }

      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kRate.kDisplacement;
      }

      @Override
      public double pidGet() {
        return motor0.getEncVelocity();
      }
    }, value -> {
      acc += value;
      motor0.set( acc );
    });
*/

//    SmartDashboard.putData("MOTOR_0_CONTROLLER", motor0Controller);
    SmartDashboard.putNumber("MOTOR_0_P", prefs.getDouble("MOTOR_0_P", -0.000005));
    SmartDashboard.putNumber("MOTOR_0_I", prefs.getDouble("MOTOR_0_I", -0.000000));
    SmartDashboard.putNumber("MOTOR_0_D", prefs.getDouble("MOTOR_0_D", -0.000005));
    SmartDashboard.putNumber("MOTOR_0_F", prefs.getDouble("MOTOR_0_F", 0));
    SmartDashboard.putNumber("MOTOR_0_SETPOINT", prefs.getDouble("MOTOR_0_SETPOINT", 8000.0));
    SmartDashboard.putNumber("MOTOR_0_SET", 0);

    //motor0Controller.enable();
  }

  @Override
  public void teleopInit() {
    motor0.set(0);
    //motor0Controller.enable();
    //acc = 0;
  }

  @Override
  public void teleopPeriodic() {
    //motor0Controller.setSetpoint(SmartDashboard.getNumber("MOTOR_0_SETPOINT", 0));
    motor0.setSetpoint(SmartDashboard.getNumber("MOTOR_0_SETPOINT", 0));

    //motor0Controller.setPID(
    motor0.setPID(
        SmartDashboard.getNumber("MOTOR_0_P", 0),
        SmartDashboard.getNumber("MOTOR_0_I", 0),
        SmartDashboard.getNumber("MOTOR_0_D", 0)
    );

    motor0.setF(SmartDashboard.getNumber("MOTOR_0_F", 0));

//    SmartDashboard.putNumber("MOTOR_0_ERROR", motor0Controller.getError());
    SmartDashboard.putNumber("MOTOR_0_ERROR", motor0.getError());

//    SmartDashboard.putNumber("MOTOR_0_POWER", acc);
    SmartDashboard.putNumber("MOTOR_0_POWER", motor0.get());

    SmartDashboard.putNumber("MOTOR_0_VELOCITY", motor0.getEncVelocity());
    SmartDashboard.putNumber("MOTOR_0_VELOCITY", motor0.getSpeed());

//    motor0.set(-SmartDashboard.getNumber("MOTOR_0_SET", 0));
  }

  @Override
  public void disabledInit() {
//    motor0Controller.disable();
//    acc = 0;

    motor0.set(0);

    prefs.putDouble("MOTOR_0_P", SmartDashboard.getNumber("MOTOR_0_P"));
    prefs.putDouble("MOTOR_0_I", SmartDashboard.getNumber("MOTOR_0_I"));
    prefs.putDouble("MOTOR_0_D", SmartDashboard.getNumber("MOTOR_0_D"));
    prefs.putDouble("MOTOR_0_F", SmartDashboard.getNumber("MOTOR_0_F"));
    prefs.putDouble("MOTOR_0_SETPOINT", SmartDashboard.getNumber("MOTOR_0_SETPOINT"));
  }
}
