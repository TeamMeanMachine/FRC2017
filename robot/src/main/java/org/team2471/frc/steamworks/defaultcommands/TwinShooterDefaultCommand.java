package org.team2471.frc.steamworks.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.Robot;

public class TwinShooterDefaultCommand extends Command{
  public TwinShooterDefaultCommand() {
    requires(Robot.twinShooter);
  }

  @Override
  protected void execute() {
    if(IOMap.hoodDPad.isUp()) {
      Robot.twinShooter.extendHood();
    } else if(IOMap.hoodDPad.isDown()) {
      Robot.twinShooter.retractHood();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
