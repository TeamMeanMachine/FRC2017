package org.team2471.frc.steamworks.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.commands.FindRopeCommand;
import org.team2471.frc.steamworks.commands.ManualClimbCommand;
import org.team2471.frc.steamworks.commands.RumbleCommand;

public class ManualClimbCommandGroup extends CommandGroup {
  public ManualClimbCommandGroup() {
    setInterruptible(false);

    addSequential(new FindRopeCommand());
    addParallel(new RumbleCommand(IOMap.getDriverController(), 1, RumbleCommand.StickSide.BOTH), 1);

    addSequential(new ManualClimbCommand());
  }
}
