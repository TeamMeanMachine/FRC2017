package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.comm.CoProcessor;
import org.team2471.frc.steamworks.comm.VisionData;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ZeroGyroCommand;
import org.team2471.frc.steamworks.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
  public static CoProcessor coProcessor;
  public static Drive drive;
  public static GearIntake gearIntake;
  public static FuelIntake fuelIntake;
  public static Shooter shooter;
  public static LEDController ledController;

  public static SendableChooser autoChooser;

  @SuppressWarnings("unchecked")

  @Override
  public void robotInit() {
    NetworkTable nt = NetworkTable.getTable("SmartDashboard");
    nt.getKeys().forEach(nt::clearPersistent);
    shooter = new Shooter();
    drive = new Drive();
    gearIntake = new GearIntake();
    fuelIntake = new FuelIntake();
    ledController = new LEDController();
    HardwareMap.init();

    coProcessor = new CoProcessor();
    IOMap.init();

    autoChooser = new SendableChooser();
    autoChooser.addObject("Don't Move", new DoNothingAuto());
    autoChooser.addObject("Drive to Hopper", new DriveToHopperAuto());
    autoChooser.addObject("Drive Eight Feet", new DriveEightFeet(1.0));
    autoChooser.addObject("Drive to left Lift", new DriveToLeftLiftAuto());
    autoChooser.addObject("Drive to right Lift", new DriveToBoilerLift());
    autoChooser.addObject("Drive to middle lift", new DriveToLift(1.0));
    autoChooser.addObject("One Hundred point Auto", new OneHundredPointAuto());
    autoChooser.addObject("Drop off gear and go to far Hopper", new GearPlusFarHopper());
    autoChooser.addObject("Circle Auto", new CircleTestAutonomous(1.0));
    autoChooser.addObject("CoOp Hopper", new CoOpHopper());
    autoChooser.addObject("One Hundred point Auto, Fuel first", new SecondOneHundredPointAuto());
    autoChooser.addObject("Backwards test", new DriveBackwardsFromLLToHopper(1.0,false));
    autoChooser.addObject("Just Shoot Auto", new AimCommand());

    SmartDashboard.putData("AutoChooser", autoChooser);
    SmartDashboard.putData(new ZeroGyroCommand());

    drive.disableClimbing();
  }

    @Override
    public void autonomousInit() {
      if (autoChooser != null) {
        Command autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
          autonomousCommand.start();
        }
      }
      HardwareMap.gyro.reset();
    }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Drive Speed", drive.getSpeed());
    SmartDashboard.putNumber("Gear Sensor", HardwareMap.GearIntakeMap.gearSensor.getValue());

    SmartDashboard.putNumber("Gyro", drive.getAngle());

    VisionData boilerData = coProcessor.getBoilerData();
    SmartDashboard.putString("Boiler", boilerData.targetPresent() ? Double.toString(boilerData.getError()) : "NONE"); // don't use this number for real stuff
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {
    VisionData boilerData = coProcessor.getBoilerData();
    SmartDashboard.putString("Boiler", boilerData.targetPresent() ? Double.toString(boilerData.getError()) : "NONE"); // don't use this number for real stuff
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }
}
