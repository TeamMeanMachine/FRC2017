package org.team2471.frc.steamworks.subsystems;

import com.ctre.CANTalon;

import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.defaultcommands.TwinShooterDefaultCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TwinShooter extends Subsystem {

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


  /** Enable... This really needs no explanation.
   */
  public void enable() {
    masterLeft.enable();
    masterRight.enable();
  }

  /** Disable...
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
    public void ballFeederOut() {
      ballFeeder.set(0);
    }

    public void ballFeederIn() {
      ballFeeder.set(0);
    }


    @Override
    protected void initDefaultCommand() {
    setDefaultCommand(new TwinShooterDefaultCommand());
    }
  }

