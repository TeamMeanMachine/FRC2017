package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.steamworks.commands.FuelIntakeCommand;

public class IOMap {
  private static Controller driverController = new Controller(0);
  private static Controller coDriverController = new Controller(1);

  public static ControllerButton shootButton = coDriverController.getButton(1);
  public static final ControllerAxis throttleAxis = driverController.getAxis(1)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis turnAxis = driverController.getAxis(4)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis leftAxis = driverController.getAxis(2);
  public static final ControllerAxis rightAxis = driverController.getAxis(3);

  public static final ControllerButton intakeButton = driverController.getButton(5);


  public static final ControllerButton turnInPlaceButton = driverController.getButton(0);


  public static void init () {
    CommandTrigger intakeTrigger = new CommandTrigger(intakeButton::get);
    intakeTrigger.whileActive(new FuelIntakeCommand());
  }
}
