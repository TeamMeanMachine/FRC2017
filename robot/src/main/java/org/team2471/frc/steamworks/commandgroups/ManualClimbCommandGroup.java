package org.team2471.frc.steamworks.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.FindRopeCommand;
import org.team2471.frc.steamworks.commands.ManualClimbCommand;

public class ManualClimbCommandGroup extends CommandGroup {
  public ManualClimbCommandGroup() {
    addSequential(new FindRopeCommand());
    addSequential(new ManualClimbCommand());
  }
}
