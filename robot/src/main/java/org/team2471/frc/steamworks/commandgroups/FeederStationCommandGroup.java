package org.team2471.frc.steamworks.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team2471.frc.steamworks.commands.TiltGearIntakeCommand;
import org.team2471.frc.steamworks.commands.FeedGearCommand;

public class FeederStationCommandGroup extends CommandGroup {
  public FeederStationCommandGroup() {
    addSequential(new FeedGearCommand());
    addSequential(new TiltGearIntakeCommand());
  }
}
