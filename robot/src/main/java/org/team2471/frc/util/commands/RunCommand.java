package org.team2471.frc.util.commands;

import edu.wpi.first.wpilibj.command.Command;

public class RunCommand extends Command {
  private final Runnable runnable;

  public RunCommand(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  protected void initialize() {
    runnable.run();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
