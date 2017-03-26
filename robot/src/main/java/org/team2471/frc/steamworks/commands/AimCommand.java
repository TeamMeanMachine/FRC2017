package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.lib.motion_profiling.MotionCurve;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.subsystems.UPBoard;

public class AimCommand extends PIDCommand {
  private final double AGITATOR_DELAY = 0.5; // time between each agitator interval
  private final double AGITATOR_DURATION = 1.5; // time to extend gear intake while agitation active
  private final double AUTO_SHOOT_DELAY = 0.5;

  private final Timer agitatorTimer = new Timer();
  private final Timer shootingTimer = new Timer();
  private final PIDController turnController = getPIDController();

  private boolean wasExtended;
  private double offset;

  private boolean targetFound;

  private int lastImageNumber = 0;
  private double lastImageTimestamp = Timer.getFPGATimestamp();
  private double startTime;

  private MotionCurve curveDistanceToRPM;

  public AimCommand() {
    super(0.07, 0, 0.1);
    requires(Robot.drive);
    requires(Robot.gearIntake);
    requires(Robot.fuelIntake);
    requires(Robot.shooter);
    requires(Robot.coProcessor);

    Robot.coProcessor.setState(UPBoard.State.BOILER);

    DashboardUtils.putPersistantNumber("Aim Offset", 0);

    turnController.setAbsoluteTolerance(2.0);
    turnController.setToleranceBuffer(30);
  }

  protected void initialize() {
    Robot.shooter.enable();
    agitatorTimer.start();
    shootingTimer.start();

    startTime = Timer.getFPGATimestamp();

    targetFound = false;

    offset = SmartDashboard.getNumber("Aim Offset", 0);

    curveDistanceToRPM = new MotionCurve();
    curveDistanceToRPM.storeValue(0.0, SmartDashboard.getNumber("RPM1", 2640.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist1", 5.5), SmartDashboard.getNumber("RPM1",2640.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist2", 8.25), SmartDashboard.getNumber("RPM2",2910.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist3", 10.25), SmartDashboard.getNumber("RPM3",3160.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist4", 13.5), SmartDashboard.getNumber("RPM4",4160.0));
    // RPM0 is for the boiler shot
  }

  protected void execute() {
    boolean autonomous = DriverStation.getInstance().isAutonomous();

    Robot.fuelIntake.retract();

    double angle = returnPIDInput();
    if (SmartDashboard.getBoolean("Auto Aim", false)) {

      if(Robot.coProcessor.isDataPresent()) {
        double error = Robot.coProcessor.getError().getAsDouble();
        double distance = Robot.coProcessor.getDistance().getAsDouble();
        angle -= error * 0.7;
        targetFound = true;

        if (Robot.shooter.isHoodUp()) {
          double rpm = curveDistanceToRPM.getValue(distance);
          System.out.println("Distance: " + distance + " RPM: " + rpm);
          Robot.shooter.setSetpoint(rpm + SmartDashboard.getNumber("Shooter OffSet", 0.0));
        } else {
          angle += IOMap.aimAxis.get() * 7.5;
          Robot.shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0.0));
        }
      }
    } else {
      // manual aim
      angle += IOMap.aimAxis.get() * 7.5;
      Robot.shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0.0));
    }
    turnController.setSetpoint(angle);

    // shooting and agitator

    if(autonomous) {
      Robot.shooter.extendHood();
    }

    boolean shoot = autonomous ?
        turnController.getAvgError() < 2  && targetFound :  // auto aim condition
        IOMap.shootButton.get(); // manual aim condition
    if (shoot) {
      shootingTimer.reset();
      double speed = autonomous ?
          // auto
          Timer.getFPGATimestamp() > startTime + AUTO_SHOOT_DELAY ? 0.9 : 0
          // teleop
          : (IOMap.shootAxis.get() - 0.15) * 1/(1 - 0.15);

      if (!Robot.shooter.isHoodUp()) {  // boiler shot
        speed *= SmartDashboard.getNumber("BoilerMaxFeed", 0.75 );
      }

      Robot.shooter.setIntake(speed * 0.8, speed);
      Robot.fuelIntake.rollIn();

      if(agitatorTimer.get() > AGITATOR_DELAY + AGITATOR_DURATION) {
        agitatorTimer.reset();
      } else if(agitatorTimer.get() > AGITATOR_DELAY) {
        Robot.gearIntake.extend();
      } else {
        Robot.gearIntake.retract();
      }
    } else {
      Robot.gearIntake.retract();
      if(shootingTimer.get() < 0.2){
        Robot.shooter.setIntake(0, 0);
        Robot.fuelIntake.stopRoll();
        agitatorTimer.reset();
      }
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
    SmartDashboard.putNumber("Aim Error", turnController.getError());
  }

  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
    Robot.shooter.disable();
    agitatorTimer.stop();
    turnController.disable();
    Robot.shooter.reset();
    Robot.gearIntake.retract();

    if(wasExtended) {
      Robot.fuelIntake.extend();
    } else {
      Robot.fuelIntake.retract();
    }

    Robot.coProcessor.setState(UPBoard.State.IDLE);
  }

  @Override
  protected double returnPIDInput() {
    return Robot.drive.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.arcade(IOMap.coDriverThrottleAxis.get(), output);
  }
}

