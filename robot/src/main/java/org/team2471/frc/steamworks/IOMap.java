package org.team2471.frc.steamworks;

import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;

public class IOMap {
  private static Controller coDriverController = new Controller(1);

  public static ControllerButton shootButton = coDriverController.getButton(1);
}
