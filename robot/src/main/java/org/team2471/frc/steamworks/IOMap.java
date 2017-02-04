package org.team2471.frc.steamworks;

import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;

public class IOMap {
  private static Controller driverController = new Controller(0);
  private static Controller coPilotController = new Controller(1);

  public static final ControllerAxis throttleAxis = driverController.getAxis(1)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis turnAxis = driverController.getAxis(4)
      .withDeadband(.2)
      .withExponentialScaling(2);



  public static final ControllerButton turnInPlaceButton = driverController.getButton(0);
}
