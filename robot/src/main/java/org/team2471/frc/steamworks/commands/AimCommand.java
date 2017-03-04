package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.comm.VisionData;

public class AimCommand extends PIDCommand {
  private final double AGITATOR_DELAY = 1.5; // time between each agitator interval
  private final double AGITATOR_DURATION = 0.5; // time to extend gear intake while agitation active

  private final Timer agitatorTimer = new Timer();
  private final PIDController turnController = getPIDController();

  private boolean wasExtended;

  public AimCommand() {
    super(1.0/45.0, 0, 1.0/45.0);
    requires(Robot.shooter);
    requires(Robot.drive);
    requires(Robot.gearIntake);
    requires(Robot.fuelIntake);

    turnController.setAbsoluteTolerance(0.1);
    turnController.setToleranceBuffer(10);
  }

  protected void initialize() {
    Robot.shooter.enable();
    agitatorTimer.start();

    wasExtended = Robot.fuelIntake.isExtended();
  }

  protected void execute() {
    Robot.fuelIntake.retract();
    Robot.gearIntake.retract();

    // set rpms
    Robot.shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0.0));

    // turning
    double angle = Robot.drive.getAngle();
    if(SmartDashboard.getBoolean("Auto Aim", false)) {
      VisionData boilerData = Robot.coProcessor.getBoilerData();
      if(boilerData.targetPresent()) {
        angle += boilerData.getError();
      }
    } else {
      // manual aim
      angle += IOMap.aimAxis.get() * 15;
    }
    turnController.setSetpoint(angle);

    // shooting and agitator
    boolean shoot = SmartDashboard.getBoolean("Auto Aim", false) ?
        turnController.onTarget() :  // auto aim condition
        IOMap.shootButton.get(); // manual aim condition
    if (shoot) {
      Robot.shooter.setIntake(0.8, 1.0);
      Robot.fuelIntake.rollIn();

      if(agitatorTimer.get() > AGITATOR_DELAY + AGITATOR_DURATION) {
        System.out.println("RESET: " + agitatorTimer.get());
        agitatorTimer.reset();
      } else if(agitatorTimer.get() > AGITATOR_DELAY) {
        Robot.gearIntake.retract();
        System.out.println("EXTEND: " + agitatorTimer.get());
      } else {
        Robot.gearIntake.extend();
        System.out.println("RETRACT: " + agitatorTimer.get());
      }
    } else {
      Robot.shooter.setIntake(0, 0);
//      Robot.gearIntake.extend();
      Robot.fuelIntake.stopRoll();
      agitatorTimer.reset();
    }

    // Output necessary data to dashboard
    SmartDashboard.putNumber("Shooter Right Speed", Robot.shooter.getRightSpeed());
    SmartDashboard.putNumber("Shooter Right Error", -Robot.shooter.getRightError());
    SmartDashboard.putNumber("Shooter Right Power", Robot.shooter.getRightOutput());

    SmartDashboard.putNumber("Shooter Left Speed", Robot.shooter.getLeftSpeed());
    SmartDashboard.putNumber("Shooter Left Error", Robot.shooter.getLeftError());
    SmartDashboard.putNumber("Shooter Left Power", Robot.shooter.getLeftOutput());

    Robot.shooter.setPID(SmartDashboard.getNumber("Shooter P", 0.02D),
        SmartDashboard.getNumber("Shooter I", 0.0D), SmartDashboard.getNumber("Shooter D", 0.0D),
        SmartDashboard.getNumber("Shooter Left F", 0.0D), SmartDashboard.getNumber("Shooter Right F", 0));
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.shooter.disable();
    agitatorTimer.stop();
    turnController.disable();
    Robot.shooter.reset();

    if(wasExtended) {
      Robot.fuelIntake.extend();
    } else {
      Robot.fuelIntake.retract();
    }
  }

  @Override
  protected double returnPIDInput() {
    return Robot.drive.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.turnInPlace(output);
  }
}

