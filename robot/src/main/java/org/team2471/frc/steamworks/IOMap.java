package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.lib.io.ControllerDPad;
import org.team2471.frc.steamworks.commands.FeedFuelCommand;
import org.team2471.frc.steamworks.commands.FuelIntakeCommand;
import org.team2471.frc.steamworks.commands.FeedGearCommand;

public class IOMap {
  private static Controller driverController = new Controller(0);
  private static Controller coDriverController = new Controller(1);

  public static ControllerButton shootButton = coDriverController.getButton(1);
  public static final ControllerAxis throttleAxis = driverController.getAxis(1)
      .withDeadband(.2)
      .withInvert()
      .withExponentialScaling(2);

  public static final ControllerAxis turnAxis = driverController.getAxis(4)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis leftAxis = driverController.getAxis(2);
  public static final ControllerAxis rightAxis = driverController.getAxis(3);


  public static final ControllerButton intakeButton = driverController.getButton(5);

  public static final ControllerButton gearFeedButton = coDriverController.getButton(7);
  public static final ControllerButton fuelFeedButton = coDriverController.getButton(9);

  public static final ControllerDPad hoodDPad = coDriverController.getDPad();

  public static void init() {
    // null checks because subsystems may not be initialized when we are testing
    if(Robot.fuelIntake != null) {
      CommandTrigger fuelIntakeTrigger = new CommandTrigger(intakeButton::get);
      fuelIntakeTrigger.whileActive(new FuelIntakeCommand());
    }

    if(Robot.gearIntake != null) {
      CommandTrigger feedGearTrigger = new CommandTrigger(gearFeedButton::get);
      feedGearTrigger.whileActive(new FeedGearCommand());

      CommandTrigger feedFuelTrigger = new CommandTrigger(fuelFeedButton::get);
      feedFuelTrigger.whileActive(new FeedFuelCommand());
    }
  }
}
