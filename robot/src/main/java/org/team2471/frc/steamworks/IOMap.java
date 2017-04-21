package org.team2471.frc.steamworks;

import org.team2471.frc.lib.control.CommandTrigger;
import org.team2471.frc.lib.io.Controller;
import org.team2471.frc.lib.io.ControllerAxis;
import org.team2471.frc.lib.io.ControllerButton;
import org.team2471.frc.lib.io.ControllerDPad;
import org.team2471.frc.lib.io.maps.XboxMap;
import org.team2471.frc.steamworks.commandgroups.ManualClimbCommandGroup;
import org.team2471.frc.steamworks.commandgroups.PickupGearCommandGroup;
import org.team2471.frc.steamworks.commands.*;
import org.team2471.frc.util.DelayedCommand;

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
  public static final ControllerButton exitClimbButton = driverController.getButton(XboxMap.Buttons.B);


  public static final ControllerButton signalCoDriverButton = driverController.getButton(XboxMap.Buttons.RIGHT_THUMBSTICK);


  public static final ControllerButton climbIntakeOverrideButton = driverController.getButton(XboxMap.Buttons.BACK);

  public static final ControllerButton fuelFlapButton = driverController.getButton(XboxMap.Buttons.X);

  //Co-Driver controls
  public static final ControllerButton signalDriverButton = coDriverController.getButton(XboxMap.Buttons.RIGHT_BUMPER);


  public static ControllerAxis shootAxis = coDriverController.getAxis(3);

  public static final ControllerButton toggleAutomaticIntakeButton = coDriverController.getButton(XboxMap.Buttons.B);
  public static final ControllerAxis spitAxis = coDriverController.getAxis(XboxMap.Axes.LEFT_TRIGGER);
  public static final ControllerButton spitButton = () -> spitAxis.get() > 0.2;
  public static final ControllerButton aimButton = coDriverController.getButton(XboxMap.Buttons.X);
  public static final ControllerButton wallButton = coDriverController.getButton(XboxMap.Buttons.Y);


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
    CommandTrigger toggleAutomaticIntakeTrigger = toggleAutomaticIntakeButton.asTrigger();
    toggleAutomaticIntakeTrigger.whenActive(new ToggleAutomaticFuelIntakeCommand());

    CommandTrigger spitFuelTrigger = spitButton.asTrigger();
    spitFuelTrigger.whileActive(new SpitFuelCommand());

    CommandTrigger fuelFlapTrigger = fuelFlapButton.asTrigger();
    fuelFlapTrigger.toggleWhenActive(new ExtendFuelFlapCommand());

    CommandTrigger pickupGearTrigger = pickupGearButton.asTrigger();
    pickupGearTrigger.whileActive(new PickupGearCommandGroup());
    pickupGearTrigger.whenInactive(new DelayedCommand(new CenterGearCommand(), 0.5, 0.8));

    CommandTrigger placeGearTrigger = placeGearButton.asTrigger();
    placeGearTrigger.whileActive(new PlaceGearCommand());

    CommandTrigger climbTrigger = climbButton.asTrigger();
    climbTrigger.whenActive(new ManualClimbCommandGroup());

    CommandTrigger aimTrigger = aimButton.asTrigger();
    aimTrigger.toggleWhenActive(new AimCommand());

    CommandTrigger signalDriverTrigger = signalDriverButton.asTrigger();
    signalDriverTrigger.whileActive(new RumbleCommand(driverController, 1, RumbleCommand.StickSide.RIGHT));

    CommandTrigger signalCoDriverTrigger = signalCoDriverButton.asTrigger();
    signalCoDriverTrigger.whileActive(new RumbleCommand(coDriverController, 1, RumbleCommand.StickSide.LEFT));

    CommandTrigger extendHoodTrigger = new CommandTrigger(shooterDPad::isUp);
    extendHoodTrigger.whenActive(new ExtendHoodCommand());

    CommandTrigger retractHoodTrigger = new CommandTrigger(shooterDPad::isDown);
    retractHoodTrigger.whenActive(new RetractHoodCommand());

    CommandTrigger incrementRPMTrigger = new CommandTrigger(shooterDPad::isRight);
    incrementRPMTrigger.whenActive(new UpdateRPMCommand(10));

    CommandTrigger decrementRPMTrigger = new CommandTrigger(shooterDPad::isLeft);
    decrementRPMTrigger.whenActive(new UpdateRPMCommand(-10));

    CommandTrigger activateWallsTrigger = wallButton.asTrigger();
    activateWallsTrigger.toggleWhenActive(new ExtendHopperWallsCommand());
  }

  public static Controller getDriverController() {
    return driverController;
  }

  public static Controller getGunnerController() {
    return coDriverController;
  }
}
