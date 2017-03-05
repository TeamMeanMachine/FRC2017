package org.team2471.frc.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class DelayedCommand extends CommandGroup {
  public DelayedCommand(Command command, double delay, double timeout) {
    addSequential(new WaitCommand(delay));
    addSequential(command, timeout);
  }

  public DelayedCommand(Command command, double delay) {
    addSequential(new WaitCommand(delay));
    addSequential(command);
  }
}
