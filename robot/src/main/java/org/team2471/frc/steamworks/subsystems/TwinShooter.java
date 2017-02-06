package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team2471.frc.steamworks.defaultcommands.TwinShooterDefaultCommand;

import static org.team2471.frc.steamworks.HardwareMap.TwinShooter.*;

public class TwinShooter extends Subsystem{

  public TwinShooter(){
    leftShooterMotor1.changeControlMode(CANTalon.TalonControlMode.Speed);
    leftShooterMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    leftShooterMotor2.setInverted(true);
    leftShooterMotor1.setPID(0, 0, 0);

    rightShooterMotor1.changeControlMode(CANTalon.TalonControlMode.Speed);
    rightShooterMotor1.setInverted(true);
    rightShooterMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
    rightShooterMotor2.setInverted(true);
    rightShooterMotor1.setPID(0, 0, 0);
  }
  public void shoot(boolean shoot, double shootRate){
    leftShooterMotor1.set(shootRate);
    rightShooterMotor1.set(shootRate);
  }
  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TwinShooterDefaultCommand());
  }
}
