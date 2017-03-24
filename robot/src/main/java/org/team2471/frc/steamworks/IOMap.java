package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.lib.io.ControllerDPad;
import org.team2471.frc.lib.io.maps.XboxMap;
import org.team2471.frc.steamworks.commandgroups.ManualClimbCommandGroup;
import org.team2471.frc.steamworks.commands.*;

@SuppressWarnings("WeakerAccess")
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

  public static final ControllerButton pickupGearButton = driverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);
  public static final ControllerButton placeGearButton = driverController.getButton(XboxMap.Buttons.LEFT_BUMPER);

  public static final ControllerButton climbButton = driverController.getButton(XboxMap.Buttons.Y);


  public static final ControllerButton signalCoDriverButton = driverController.getButton(XboxMap.Buttons.RIGHT_THUMBSTICK);


  public static final ControllerButton climbIntakeOverrideButton = driverController.getButton(XboxMap.Buttons.BACK);

  //Co-Driver controls
  public static final ControllerButton signalDriverButton = coDriverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);


  public static ControllerAxis shootAxis = coDriverController.getAxis(3);

  public static final ControllerButton toggleIntakeButton = coDriverController.getButton(XboxMap.Buttons.B);
  public static final ControllerButton useIntakeButton = coDriverController.getButton(XboxMap.Buttons.LEFT_BUMPER);
  public static final ControllerAxis spitAxis = coDriverController.getAxis(XboxMap.Axes.LEFT_TRIGGER);
  public static final ControllerButton spitButton = () -> spitAxis.get() > 0.2;
  public static final ControllerButton fuelFeedButton = coDriverController.getButton(XboxMap.Buttons.A);
  public static final ControllerButton aimButton = coDriverController.getButton(XboxMap.Buttons.X);


  public static ControllerButton shootButton = () -> shootAxis.get() > 0.15;

  public static final ControllerAxis aimAxis = coDriverController.getAxis(XboxMap.Axes.RIGHT_THUMBSTICK_X)
      .withDeadband(0.2)
      .withExponentialScaling(2);

  public static final ControllerDPad shooterDPad = coDriverController.getDPad();

  public static final ControllerAxis coDriverThrottleAxis = coDriverController.getAxis(XboxMap.Axes.LEFT_THUMBSTICK_Y)
      .withDeadband(.2)
      .withInvert()
      .withExponentialScaling(2)
      .withLinearScaling(0.6);

  public static void init() {
    CommandTrigger fuelIntakeTrigger = new CommandTrigger(toggleIntakeButton::get);
    fuelIntakeTrigger.toggleWhenActive(new FuelIntakeCommand());


    CommandTrigger feedFuelTrigger = new CommandTrigger(fuelFeedButton::get);

    CommandTrigger pickupGearTrigger = new CommandTrigger(pickupGearButton::get);
    pickupGearTrigger.whileActive(new PickupGearCommand());

    CommandTrigger placeGearTrigger = new CommandTrigger(placeGearButton::get);
    placeGearTrigger.whileActive(new PlaceGearCommand());

    CommandTrigger climbTrigger = new CommandTrigger(climbButton::get);
    climbTrigger.toggleWhenActive(new ManualClimbCommandGroup());

    CommandTrigger aimTrigger = new CommandTrigger(aimButton::get);
    aimTrigger.toggleWhenActive(new AimCommand());

    CommandTrigger signalDriverTrigger = new CommandTrigger(signalDriverButton::get);
    signalDriverTrigger.whileActive(new RumbleCommand(driverController, 1, RumbleCommand.StickSide.RIGHT));

    CommandTrigger signalCoDriverTrigger = new CommandTrigger(signalCoDriverButton::get);
    signalCoDriverTrigger.whileActive(new RumbleCommand(coDriverController, 1, RumbleCommand.StickSide.LEFT));

    CommandTrigger extendHoodTrigger = new CommandTrigger(shooterDPad::isUp);
    extendHoodTrigger.whenActive(new ExtendHoodCommand());

    CommandTrigger retractHoodTrigger = new CommandTrigger(shooterDPad::isDown);
    retractHoodTrigger.whenActive(new RetractHoodCommand());

    CommandTrigger incrementRPMTrigger = new CommandTrigger(shooterDPad::isRight);
    incrementRPMTrigger.whenActive(new UpdateRPMCommand(10));

    CommandTrigger decrementRPMTrigger = new CommandTrigger(shooterDPad::isLeft);
    decrementRPMTrigger.whenActive(new UpdateRPMCommand(-10));
  }

  public static Controller getDriverController() {
    return driverController;
  }
}
