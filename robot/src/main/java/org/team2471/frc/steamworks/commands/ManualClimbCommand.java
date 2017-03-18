package org.team2471.frc.steamworks.commands;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.motion_profiling.MotionProfileAnimation;
import org.team2471.frc.lib.motion_profiling.MotionProfileCurve;
import org.team2471.frc.lib.motion_profiling.PlayAnimationCommand;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

import static org.team2471.frc.steamworks.IOMap.toggleIntakeButton;


public class ManualClimbCommand extends PlayAnimationCommand {
  private double startDistance;
  private Timer timer;
  private boolean automaticIntake;

  private MotionProfileAnimation animation;
  private MotionProfileCurve leftCurve;
  private MotionProfileCurve rightCurve;

  public ManualClimbCommand() {
    requires(Robot.drive);
    setInterruptible(false);
    timer = new Timer();

    animation = new MotionProfileAnimation();
    leftCurve = new MotionProfileCurve(Robot.drive.getLeftMotor1(), animation );
    rightCurve = new MotionProfileCurve(Robot.drive.getRightMotor1(), animation );

    leftCurve.storeValue(0.0, 0.0 );
//    leftCurve.storeValue(1.5, -12.5 );
    leftCurve.storeValue(3.5, -29.0 );
    leftCurve.storeValue(15.0, -40.0 );

    rightCurve.storeValue(0.0, 0.0 );
//    rightCurve.storeValue(1.5, -12.5 );
    rightCurve.storeValue(3.5, -29.0 );
    rightCurve.storeValue(15.0, -40.0 );

    setAnimation(animation);
  }

  @Override
  protected void initialize() {
    super.initialize();
    timer.start();
    Robot.drive.enableClimbing();
    startDistance = Robot.drive.getDistance();

    Robot.drive.getLeftMotor1().changeControlMode(CANTalon.TalonControlMode.Position);
    Robot.drive.getRightMotor1().changeControlMode(CANTalon.TalonControlMode.Position);
    Robot.drive.hiGear();

    leftCurve.setOffset(Robot.drive.getLeftMotor1().getPosition());
    rightCurve.setOffset(Robot.drive.getRightMotor1().getPosition());
    automaticIntake = true;
  }

  @Override
  protected void execute() {
    setSpeed(IOMap.playAnimationAxis.get() - IOMap.reverseAnimationAxis.get());
    super.execute();

    double distance = Math.abs(Robot.drive.getDistance() - startDistance);
//    if (automaticIntake) {
      if (distance > 18) {
        Robot.fuelIntake.extend();
      } else {
        Robot.fuelIntake.retract();
      }
//    }
//
//    if (toggleIntakeButton.get()) {
//      Robot.fuelIntake.toggle();
//      automaticIntake = false;
//    }

    SmartDashboard.putNumber("Climb Time", timer.get());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    timer.stop();
    Robot.drive.getLeftMotor1().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    Robot.drive.getRightMotor1().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
  }
}
