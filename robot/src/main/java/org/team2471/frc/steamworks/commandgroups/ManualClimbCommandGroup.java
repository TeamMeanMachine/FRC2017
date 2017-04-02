package org.team2471.frc.steamworks.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.commands.FindRopeCommand;
import org.team2471.frc.steamworks.commands.RumbleCommand;
import org.team2471.frc.steamworks.commands.SuperManualClimbCommand;

public class ManualClimbCommandGroup extends CommandGroup {
  public ManualClimbCommandGroup() {
    addSequential(new FindRopeCommand());
    addParallel(new RumbleCommand(IOMap.getDriverController(), 1, RumbleCommand.StickSide.BOTH), 1);

    addSequential(new SuperManualClimbCommand());
  }
}
