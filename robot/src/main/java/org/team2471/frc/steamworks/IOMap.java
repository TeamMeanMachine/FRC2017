package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.lib.io.ControllerDPad;
import org.team2471.frc.lib.io.maps.XboxMap;
import org.team2471.frc.steamworks.commandgroups.ManualClimbCommandGroup;
import org.team2471.frc.steamworks.commands.*;

public class IOMap {
  private static Controller driverController = new Controller(0);
  private static Controller coDriverController = new Controller(1);

  // Driver controls
  public static final ControllerAxis throttleAxis = driverController.getAxis(XboxMap.Axes.LEFT_THUMBSTICK_Y)
      .withDeadband(.2)
      .withInvert()
      .withExponentialScaling(2);

  public static final ControllerAxis turnAxis = driverController.getAxis(XboxMap.Axes.RIGHT_THUMBSTICK_X)
      .withDeadband(.2)
      .withExponentialScaling(2);

  public static final ControllerAxis leftAxis = driverController.getAxis(XboxMap.Axes.LEFT_TRIGGER);
  public static final ControllerAxis rightAxis = driverController.getAxis(XboxMap.Axes.RIGHT_TRIGGER);

  public static final ControllerAxis playAnimationAxis = driverController.getAxis(XboxMap.Axes.LEFT_TRIGGER)
      .withExponentialScaling(2);
  public static final ControllerAxis reverseAnimationAxis = driverController.getAxis(XboxMap.Axes.RIGHT_TRIGGER)
      .withExponentialScaling(2);

  public static final ControllerButton toggleIntakeButton = driverController.getButton(XboxMap.Buttons.X);
  public static final ControllerButton useIntakeButton = driverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);
  public static final ControllerButton spitButton = driverController.getButton(XboxMap.Buttons.LEFT_BUMPER);
  public static final ControllerButton climbButton = driverController.getButton(XboxMap.Buttons.Y);
  public static final ControllerButton gearButton = driverController.getButton(XboxMap.Buttons.A);

  public static final ControllerButton signalCoDriverButton = driverController.getButton(XboxMap.Buttons.START);

  //Co-Driver controls
  public static final ControllerButton signalDriverButton = coDriverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);

  public static final ControllerButton gearFeedButton = coDriverController.getButton(XboxMap.Buttons.Y);
  public static ControllerAxis shootAxis = coDriverController.getAxis(3);

  public static final ControllerButton fuelFeedButton = coDriverController.getButton(XboxMap.Buttons.A);
  public static final ControllerButton aimButton = coDriverController.getButton(XboxMap.Buttons.X);

  public static ControllerButton shootButton = () -> shootAxis.get() > 0.4;

  public static final ControllerAxis aimAxis = coDriverController.getAxis(XboxMap.Axes.RIGHT_THUMBSTICK_X)
      .withDeadband(0.2)
      .withExponentialScaling(2);

  public static final ControllerDPad hoodDPad = coDriverController.getDPad();

  public static void init() {
    // null checks because subsystems may not be initialized when we are testing
    CommandTrigger fuelIntakeTrigger = new CommandTrigger(toggleIntakeButton::get);
    fuelIntakeTrigger.toggleWhenActive(new FuelIntakeCommand());

    CommandTrigger feedGearTrigger = new CommandTrigger(gearFeedButton::get);
    feedGearTrigger.whileActive(new FeedGearCommand());

    CommandTrigger feedFuelTrigger = new CommandTrigger(fuelFeedButton::get);
    feedFuelTrigger.whileActive(new TiltGearIntakeCommand());

    CommandTrigger climbTrigger = new CommandTrigger(climbButton::get);
    climbTrigger.whileActive(new ManualClimbCommandGroup());

    CommandTrigger aimTrigger = new CommandTrigger(aimButton::get);
    aimTrigger.toggleWhenActive(new AimCommand());

    CommandTrigger signalDriverTrigger = new CommandTrigger(signalDriverButton::get);
    signalDriverTrigger.whileActive(new RumbleCommand(driverController, 1, RumbleCommand.StickSide.RIGHT));

    CommandTrigger signalCoDriverTrigger = new CommandTrigger(signalCoDriverButton::get);
    signalCoDriverTrigger.whileActive(new RumbleCommand(coDriverController, 1, RumbleCommand.StickSide.LEFT));
  }

  public static Controller getDriverController() {
    return driverController;
  }
}
