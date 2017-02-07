package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.Utility;
import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.steamworks.subsystems.LEDController;
import org.team2471.frc.util.commands.RunCommand;

public class IOMap {

  public static void init() {
    CommandTrigger userButtonTrigger = new CommandTrigger(Utility::getUserButton);
    userButtonTrigger.whenActive(new RunCommand(() -> Robot.ledController.sendLn(LEDController.LOAD_GEAR)));
    userButtonTrigger.whenInactive(new RunCommand(() -> Robot.ledController.sendLn(LEDController.LOAD_FUEL)));
  }
}
