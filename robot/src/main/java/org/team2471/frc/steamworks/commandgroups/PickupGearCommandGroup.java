package org.team2471.frc.steamworks.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.IOMap;
import org.team2471.frc.steamworks.commands.PickupGearCommand;
import org.team2471.frc.steamworks.commands.RumbleCommand;

public class PickupGearCommandGroup extends CommandGroup {
  public PickupGearCommandGroup() {
    addSequential(new PickupGearCommand());
    addSequential(new RumbleCommand(IOMap.getDriverController(), 0.8, RumbleCommand.StickSide.BOTH));
  }
}
