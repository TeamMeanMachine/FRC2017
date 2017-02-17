package org.team2471.frc.steamworks;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.control.MeanMotorController;
import org.team2471.frc.steamworks.autonomouscommands.DriveToLift;
import org.team2471.frc.steamworks.subsystems.TwinShooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team2471.frc.steamworks.subsystems.Drive;
import org.team2471.frc.steamworks.subsystems.GearIntake;
import org.team2471.frc.steamworks.subsystems.FuelIntake;

public class Robot extends IterativeRobot {
  public static CoProcessor coProcessor;

  public static Drive drive;

  public static GearIntake gearIntake;

  public static FuelIntake fuelIntake;

  public static TwinShooter twinShooter;

  @Override
  public void robotInit() {
    twinShooter = new TwinShooter();
    drive = new Drive();
    gearIntake = new GearIntake();
    fuelIntake = new FuelIntake();

    coProcessor = new CoProcessor();
    IOMap.init();
  }

  @Override
  public void autonomousInit() {
    new DriveToLift(1).start();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {
  }
}
