package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team2471.frc.steamworks.Robot;

public class ExtendHoodCommand extends InstantCommand {
  @Override
  protected void execute() {
    Robot.shooter.extendHood();
  }
}
