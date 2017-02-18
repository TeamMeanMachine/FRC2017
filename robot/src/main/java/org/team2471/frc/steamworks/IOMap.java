package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.lib.io.ControllerDPad;
import org.team2471.frc.lib.io.maps.LogitechJoystickMap;
import org.team2471.frc.lib.io.maps.XboxMap;
import org.team2471.frc.steamworks.commands.FeedFuelCommand;
import org.team2471.frc.steamworks.commands.FuelIntakeCommand;
import org.team2471.frc.steamworks.commands.FeedGearCommand;

public class IOMap {
  private static Controller driverController = new Controller(0);
  private static Controller coDriverController = new Controller(1);

  public static final ControllerAxis throttleAxis = driverController.getAxis(XboxMap.Axes.LEFT_THUMBSTICK_Y)
      .withDeadband(.2)
      .withInvert()
      .withExponentialScaling(2);

  public static final ControllerAxis turnAxis = driverController.getAxis(XboxMap.Axes.RIGHT_THUMBSTICK_X)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis leftAxis = driverController.getAxis(XboxMap.Axes.LEFT_TRIGGER);
  public static final ControllerAxis rightAxis = driverController.getAxis(XboxMap.Axes.RIGHT_TRIGGER);


  public static final ControllerButton toggleIntakeButton = driverController.getButton(XboxMap.Buttons.X);
  public static final ControllerButton useIntakeButton = driverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);
  public static final ControllerButton spitButton = driverController.getButton(XboxMap.Buttons.BACK);



  public static final ControllerButton gearFeedButton = coDriverController.getButton(XboxMap.Buttons.Y);
  public static ControllerAxis shootAxis = coDriverController.getAxis(3);

  public static final ControllerButton fuelFeedButton = coDriverController.getButton(XboxMap.Buttons.A);

  public static ControllerButton shootButton = () -> shootAxis.get() > 0.4;

  public static final ControllerAxis aimAxis = coDriverController.getAxis(XboxMap.Axes.RIGHT_THUMBSTICK_X)
      .withDeadband(0.2)
      .withExponentialScaling(2);

  public static final ControllerDPad hoodDPad = coDriverController.getDPad();

  public static void init() {
    // null checks because subsystems may not be initialized when we are testing
    if(Robot.fuelIntake != null) {
      CommandTrigger fuelIntakeTrigger = new CommandTrigger(toggleIntakeButton::get);
      fuelIntakeTrigger.toggleWhenActive(new FuelIntakeCommand());
    }

    if(Robot.gearIntake != null) {
      CommandTrigger feedGearTrigger = new CommandTrigger(gearFeedButton::get);
      feedGearTrigger.whileActive(new FeedGearCommand());

      CommandTrigger feedFuelTrigger = new CommandTrigger(fuelFeedButton::get);
      feedFuelTrigger.whileActive(new FeedFuelCommand());
    }
  }
}
