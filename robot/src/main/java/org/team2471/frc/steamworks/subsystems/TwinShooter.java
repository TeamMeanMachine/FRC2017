package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TwinShooter extends Subsystem {

  // 3 TALONs (2 for the actual spinny part and one to feed ball) 2 PID on slave and master

  private CANTalon masterLeft = HardwareMap.TwinShooterMap.masterLeft;
  private CANTalon slaveLeft = HardwareMap.TwinShooterMap.slaveLeft;
  private CANTalon masterRight = HardwareMap.TwinShooterMap.masterRight;
  private CANTalon slaveRight = HardwareMap.TwinShooterMap.slaveRight;
  private CANTalon ballFeeder = HardwareMap.TwinShooterMap.ballFeeder;



    public TwinShooter() {

      masterLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
      masterRight.changeControlMode(CANTalon.TalonControlMode.Speed);
      slaveLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
      slaveRight.changeControlMode(CANTalon.TalonControlMode.Follower);

    }




  /** Enable
   */
  public void enable() {
    masterLeft.enable();
    masterRight.enable();
  }

  /** Disable
   */
  public void disable() {
    masterLeft.disable();
    masterRight.disable();
  }

  /**
   * Runs master and slave forward on both
     */
    public void shooterMotorsForward() {
      masterLeft.setSetpoint(0);
      masterRight.setSetpoint(0);
    }

    /**
     * Runs the ball feeder forward on both
     */

    public void shooterMotorsBack() {
      masterLeft.setSetpoint(-0);
      masterRight.setSetpoint(-0);
    }

    /**
     * Runs the ball feeder back on both
     */
    public void feedBall() {
      ballFeeder.set(0);
    }


    @Override
    protected void initDefaultCommand() {

    }
  }

