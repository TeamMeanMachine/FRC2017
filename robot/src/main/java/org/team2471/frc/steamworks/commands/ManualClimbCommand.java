package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class ManualClimbCommand extends Command {
  private double startDistance;

  public ManualClimbCommand() {
    requires(Robot.drive);
    requires(Robot.fuelIntake);
    setInterruptible(false);
  }

  @Override
  protected void initialize() {
    Robot.drive.enableClimbing();
    startDistance = Robot.drive.getDistance();
  }

  @Override
  protected void execute() {
    Robot.drive.driveStraight(-IOMap.playAnimationAxis.get() + IOMap.reverseAnimationAxis.get(), false);
    double distance = Robot.drive.getDistance() - startDistance;
    if (distance > 10) {
      Robot.fuelIntake.extend();
    }

  }

  @Override
  protected boolean isFinished() {
    return startDistance < Robot.drive.getDistance() - 1.5;
  }

  @Override
  protected void end() {
  }
}
