package org.team2471.frc.steamworks;

import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;

public class IOMap {
  private static Controller coDriverController = new Controller(1);

  public static final ControllerAxis shooterRPM = coDriverController.getAxis(1);
}
