package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.HardwareMap;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class TwinShooterDefaultCommand extends Command{
  public TwinShooterDefaultCommand(){requires(Robot.twinShooter);}
  @Override
  protected void execute() {
    Robot.twinShooter.shoot(IOMap.shooterButton.get(), IOMap.shooterRate.get());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
