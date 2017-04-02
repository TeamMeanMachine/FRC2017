package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.lib.io.Controller;

public class RumbleCommand extends Command {
  private final Controller controller;
  private final double intensity;
  private final StickSide side;

  public RumbleCommand(Controller controller, double intensity, StickSide side) {
    this.controller = controller;
    this.intensity = intensity;
    this.side = side;
  }

  @Override
  protected void execute() {
    if (side == StickSide.LEFT) {
      controller.rumbleLeft((float) intensity);
    } else if (side == StickSide.RIGHT) {
      controller.rumbleRight((float) intensity);
    } else {
      controller.rumbleLeft((float) intensity);
      controller.rumbleRight((float) intensity);
    }
  }

  @Override
  protected boolean isFinished() {
    return /*isInterruptible() ||*/ isTimedOut();
  }

  @Override
  protected void end() {
    if (side == StickSide.LEFT) {
      controller.rumbleLeft(0.0f);
    } else if (side == StickSide.RIGHT) {
      controller.rumbleRight(0.0f);
    } else {
      controller.rumbleLeft(0.0f);
      controller.rumbleRight(0.0f);
    }
  }

  public enum StickSide {
    LEFT,
    RIGHT,
    BOTH,
  }
}
