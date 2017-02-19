package org.team2471.frc.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team2471.frc.lib.io.Controller;

public class RumbleCommand extends Command {
  private final Controller controller;

  public RumbleCommand(Controller controller) {
    this.controller = controller;
  }

  @Override
  protected void execute() {
    controller.rumbleLeft(0.8f);
    controller.rumbleRight(0.8f);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
