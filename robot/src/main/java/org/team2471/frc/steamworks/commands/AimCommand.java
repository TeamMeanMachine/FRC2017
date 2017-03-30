package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.lib.motion_profiling.MotionCurve;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;
import org.team2471.frc.steamworks.subsystems.UPBoard;

public class AimCommand extends PIDCommand {
  private final double AUTO_SHOOT_DELAY = 0.05;

  private final double AGITATOR_DELAY = 3;
  private final double AGITATOR_DURATION = 0.4;

  private final Timer shootingTimer = new Timer();
  private final Timer agitatorTimer = new Timer();
  private final PIDController turnController = getPIDController();

  private double offset;
  private double gyroAngle;
  private double rpm;

  private boolean targetFound;

  private int lastImageNumber = 0;
  private double lastImageTimestamp = Timer.getFPGATimestamp();
  private double startTime;

  private MotionCurve curveDistanceToRPM;

  public AimCommand(double gyroAngle, double rpm) {
    super(0.07, 0, 0.1);
    requires(Robot.drive);
    requires(Robot.gearIntake);
    requires(Robot.fuelIntake);
    requires(Robot.shooter);
    requires(Robot.coProcessor);
    requires(Robot.flap);

    this.gyroAngle = gyroAngle;
    this.rpm = rpm;

    Robot.coProcessor.setState(UPBoard.State.BOILER);

    DashboardUtils.putPersistentNumber("Aim Offset", 0);
    DashboardUtils.putPersistentNumber("Aim P", 0.17);
    DashboardUtils.putPersistentNumber("Aim D", 0.2);
    DashboardUtils.putPersistentNumber("Aim Output Range", 0.35);

    turnController.setAbsoluteTolerance(0.35);
  }

  public AimCommand() {
    this(0, 2500);
  }

  protected void initialize() {
    Robot.shooter.enable();
    shootingTimer.start();
    agitatorTimer.start();

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
    boolean autonomous = DriverStation.getInstance().isAutonomous();
    if (SmartDashboard.getBoolean("Auto Aim", false) || autonomous) {
      Robot.shooter.extendHood();
    }
  }

  protected void execute() {
    boolean autonomous = DriverStation.getInstance().isAutonomous();
    Robot.shooter.enableFlashlight();

    // update PID
    turnController.setPID(SmartDashboard.getNumber("Aim P", 0.07), 0, SmartDashboard.getNumber("Aim D", 0.1));
    double outputRange = SmartDashboard.getNumber("Aim Output Range", 0.5);
    turnController.setOutputRange(-outputRange, outputRange);

    double angle = autonomous && !targetFound ? gyroAngle : returnPIDInput();
    if (SmartDashboard.getBoolean("Auto Aim", false) || autonomous) {
      if(Robot.coProcessor.isDataPresent()) {
        double error = Robot.coProcessor.getError().getAsDouble();
        double distance = Robot.coProcessor.getDistance().getAsDouble();
        angle -= error * 0.7;
        targetFound = true;

        if (Robot.shooter.isHoodUp()) {
          rpm = curveDistanceToRPM.getValue(distance);
          rpm += SmartDashboard.getNumber("Shooter Offset", 0.0);
        } else {
          angle += IOMap.aimAxis.get() * 7.5;
          Robot.shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0.0));
        }
      }
    }  else {
      // manual aim
      angle += IOMap.aimAxis.get() * 7.5;
      rpm = SmartDashboard.getNumber("Shooter Setpoint", 0.0);
    }

    SmartDashboard.putNumber("Shooter Setpoint", rpm);
    Robot.shooter.setSetpoint(rpm);
    turnController.setSetpoint(angle);

    if(autonomous) {
      Robot.shooter.extendHood();
    }

    boolean shoot = autonomous ?
        turnController.getError() < 2  && targetFound:  // auto aim condition
        IOMap.shootButton.get(); // manual aim condition
    if (shoot) {
      shootingTimer.reset();
      double speed = autonomous ?
          // auto
          Timer.getFPGATimestamp() > startTime + AUTO_SHOOT_DELAY ? 1.0 : 0
          // teleop
          : (IOMap.shootAxis.get() - 0.15) * 1/(1 - 0.15);

      if (!Robot.shooter.isHoodUp()) {  // boiler shot
        speed *= SmartDashboard.getNumber("BoilerMaxFeed", 0.75 );
      }

      // agitator stuff
      if(agitatorTimer.get() < AGITATOR_DURATION) {
        Robot.gearIntake.extend();
      } else if(agitatorTimer.get() > AGITATOR_DELAY + AGITATOR_DURATION) {
        agitatorTimer.reset();
      } else {
        Robot.gearIntake.retract();
      }

      Robot.shooter.setIntake(speed * 0.8, speed);
      Robot.fuelIntake.rollIn();
    } else {
      Robot.gearIntake.retract();
      if(shootingTimer.get() < 0.2){
        Robot.shooter.setIntake(0, 0);
        Robot.fuelIntake.stopRoll();
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

    // rumble and on target calculation
    boolean onTarget = Math.abs(Robot.shooter.getLeftError())<300.0 &&
            Math.abs(Robot.shooter.getRightError())<300.0 &&
            turnController.onTarget();
    SmartDashboard.putBoolean("Shooter On Target", onTarget);
    float rumbleValue = onTarget ? 0.5f : 0.0f;
    IOMap.getGunnerController().rumbleLeft(rumbleValue);
    IOMap.getGunnerController().rumbleRight(rumbleValue);
  }

  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
    Robot.shooter.disable();
    turnController.disable();
    agitatorTimer.stop();
    shootingTimer.stop();
    Robot.shooter.reset();
    Robot.shooter.disableFlashlight();

    Robot.gearIntake.retract();

    IOMap.getGunnerController().rumbleLeft(0.0f);
    IOMap.getGunnerController().rumbleRight(0.0f);

    Robot.coProcessor.setState(UPBoard.State.IDLE);
  }

  @Override
  protected double returnPIDInput() {
    if(DriverStation.getInstance().isAutonomous() && !targetFound) {
      return HardwareMap.gyro.getAngle();
    } else {
      return Robot.drive.getAngle();
    }
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.drive.arcade(IOMap.coDriverThrottleAxis.get(), output);
  }
}

