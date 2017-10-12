package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.lib.motion_profiling.MotionCurve;
import org.team2471.frc.steamworks.ChesDroid;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

import java.util.Optional;

public class AimCommand extends PIDCommand {
  private final double AUTO_SHOOT_DELAY = 1.25;

  private final double AGITATOR_DELAY = 2.0;
  private final double AGITATOR_DURATION = 0.6;

  private final Timer shootingTimer = new Timer();
  private final Timer agitatorTimer = new Timer();
  private final PIDController turnController = getPIDController();
  private final double startRpm;
  private final double autoSpeed;
  private double offset;
  private double gyroAngle;
  private boolean targetFound;
  private boolean mirrored;

  private int lastImageNumber = 0;
  private double lastImageTimestamp = Timer.getFPGATimestamp();
  private double startTime;

  private MotionCurve curveDistanceToRPM;

  public AimCommand(double gyroAngle, double rpm, double autoSpeed, boolean mirrored) {
    super(0.09, 0, 0.1);
    requires(Robot.drive);
    requires(Robot.shooter);
    requires(Robot.flap);

    requires(Robot.walls);

    this.autoSpeed = autoSpeed;
    this.gyroAngle = mirrored ? -gyroAngle : gyroAngle;
    this.startRpm = rpm;
    this.mirrored = mirrored;

    DashboardUtils.putPersistentNumber("Aim Offset", 0);
    DashboardUtils.putPersistentNumber("Aim P", 0.17);
    DashboardUtils.putPersistentNumber("Aim D", 0.15);
    DashboardUtils.putPersistentNumber("Aim Output Range", 0.27);
    DashboardUtils.putPersistentBoolean("Auto Aim", true);
    DashboardUtils.putPersistentNumber("Aim Horizontal Offset", 0);

    turnController.setAbsoluteTolerance(6);
    turnController.setToleranceBuffer(10);
  }

  public AimCommand() {
    this(0, 2500, 1.0, false);
  }

  protected void initialize() {

    DashboardUtils.putPersistentNumber("Auto RPM", startRpm);
    Robot.drive.lowGear();
    Robot.shooter.enable();
    shootingTimer.start();
    agitatorTimer.start();

    startTime = Timer.getFPGATimestamp();

    targetFound = false;

    offset = SmartDashboard.getNumber("Aim Offset", 0);

    curveDistanceToRPM = new MotionCurve();
    curveDistanceToRPM.storeValue(0.0, SmartDashboard.getNumber("RPM1", 2640.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist1", 5.5), SmartDashboard.getNumber("RPM1", 2640.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist2", 8.25), SmartDashboard.getNumber("RPM2", 2910.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist3", 10.25), SmartDashboard.getNumber("RPM3", 3160.0));
    curveDistanceToRPM.storeValue(SmartDashboard.getNumber("Dist4", 13.5), SmartDashboard.getNumber("RPM4", 4160.0));
    // RPM0 is for the boiler shot
    boolean autonomous = DriverStation.getInstance().isAutonomous();
    if (SmartDashboard.getBoolean("Auto Aim", false) || autonomous) {
      Robot.shooter.extendHood();
    }
  }

  protected void execute() {
    boolean autonomous = DriverStation.getInstance().isAutonomous();
    Robot.shooter.enableRingLight();

    // update PID
    turnController.setPID(SmartDashboard.getNumber("Aim P", 0.07), 0, SmartDashboard.getNumber("Aim D", 0.1));
    double outputRange = SmartDashboard.getNumber("Aim Output Range", 0.5);
    turnController.setOutputRange(-outputRange, outputRange);
    double rpm = autonomous ? startRpm : SmartDashboard.getNumber("Shooter Setpoint");

    double angle = autonomous && !targetFound ? gyroAngle : returnPIDInput();
    angle += SmartDashboard.getNumber("Aim Horizontal Offset", 0);
    boolean autoAim = SmartDashboard.getBoolean("Auto Aim", false);
    if (autoAim || autonomous) {
      if (Robot.cheezDroid.isConnected()) {
        Optional<ChesDroid.VisionData> optionalData = Robot.cheezDroid.getData();
        if(optionalData.isPresent()) {
          ChesDroid.VisionData data = optionalData.get();
          angle -= data.error * 0.7;
          targetFound = true;

          if (Robot.shooter.isHoodUp()) {
            if (autonomous) {
              rpm = SmartDashboard.getNumber("Auto RPM", startRpm);
            }

            else{
              rpm = curveDistanceToRPM.getValue(data.distance);
              rpm += SmartDashboard.getNumber("Shooter Offset", 0.0);
            }

          } else {
            angle += IOMap.aimAxis.get() * 7.5;
            rpm = SmartDashboard.getNumber("Shooter Setpoint", 0.0);
          }
        }
      }
    } else {
      // manual aim
      angle += IOMap.aimAxis.get() * 7.5;
      rpm = SmartDashboard.getNumber("Shooter Setpoint", 0.0);
    }


    SmartDashboard.putNumber("Shooter Setpoint", rpm);
    Robot.shooter.setSetpoint(rpm);
    turnController.setSetpoint(angle);

    if (autonomous) {
      Robot.shooter.extendHood();
    }

    boolean shoot = autonomous ?
        turnController.getError() < 3 : //&& (targetFound || !Robot.coProcessor.isConnected()) :  // auto aim condition
        IOMap.shootButton.get(); // manual aim condition
    if (shoot) {
      shootingTimer.reset();
      Robot.shooter.setRampRate(0);
      double speed = autonomous ?
          // auto
          Timer.getFPGATimestamp() > startTime + AUTO_SHOOT_DELAY ? autoSpeed : 0
          // teleop
          : (IOMap.shootAxis.get() - 0.15) * 1 / (1 - 0.15);

      if (!Robot.shooter.isHoodUp()) {  // boiler shot
        speed *= SmartDashboard.getNumber("BoilerMaxFeed", 0.75);
      }

      // agitator stuff
      if (agitatorTimer.get() < AGITATOR_DURATION) {
        Robot.flap.extend();
        Robot.walls.retract();
      } else if (agitatorTimer.get() > AGITATOR_DELAY + AGITATOR_DURATION) {
        agitatorTimer.reset();
      } else {
        Robot.flap.retract();
        Robot.walls.extend();
      }

      Robot.shooter.intake(speed);
    } else {
      Robot.shooter.setRampRate(32);
      Robot.flap.retract();
      Robot.walls.extend();
      if (shootingTimer.get() < 0.2) {
        Robot.shooter.intake(0);
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
    boolean onTarget = Math.abs(Robot.shooter.getLeftError()) < 300.0 &&
        Math.abs(Robot.shooter.getRightError()) < 300.0 &&
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
    Robot.shooter.disableRingLight();
    Robot.walls.retract();
    Robot.flap.extend();

    IOMap.getGunnerController().rumbleLeft(0.0f);
    IOMap.getGunnerController().rumbleRight(0.0f);
  }

  @Override
  protected double returnPIDInput() {
    if (DriverStation.getInstance().isAutonomous() && !targetFound) {
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

