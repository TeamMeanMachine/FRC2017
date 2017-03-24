package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.autonomouscommands.FortyKPAAuto;
import org.team2471.frc.steamworks.autonomousroutines.*;
import org.team2471.frc.steamworks.autonomousroutines.FortyKPAAuto;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ZeroGyroCommand;
import org.team2471.frc.steamworks.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import java.util.OptionalDouble;

public class Robot extends IterativeRobot {
  public static final boolean COMPETITION = false;

  public static UPBoard coProcessor;
  public static Drive drive;
  public static FuelIntake fuelIntake;
  public static Shooter shooter;
  public static ActiveGearIntake activeGearIntake;
  public static LEDController ledController;

  public static SendableChooser autoChooser;

  @SuppressWarnings("unchecked")

  @Override
  public void robotInit() {
    shooter = new Shooter();
    drive = new Drive();
    fuelIntake = new FuelIntake();
    ledController = new LEDController();
    HardwareMap.init();

    coProcessor = new UPBoard();
    IOMap.init();

    autoChooser = new SendableChooser();
    autoChooser.addObject("Don't Move", new DoNothingAuto());
    autoChooser.addObject("40 KPA Forward", new DriveToHopperAuto());
    autoChooser.addObject("Drive Eight Feet", new DriveEightFeet());
    autoChooser.addObject("Feeder Lift", new FeederLiftAuto());
    autoChooser.addObject("Boiler Lift", new BoilerLiftAuto());
    autoChooser.addObject("Middle lift", new DriveToMiddleLift());
    autoChooser.addObject("One Hundred point Auto", new OneHundredPointAuto());
    autoChooser.addObject("Just Shoot Auto", new AimCommand());
    autoChooser.addObject("Short fuel and gear", new BoilerGearAuto());
    autoChooser.addObject("Gear plus ten fuel", new GearTenAuto());
    autoChooser.addObject("40 KPA Backwards", new FortyKPAAuto());

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

    SmartDashboard.putNumber("FakeGyro", drive.getAngle());

    OptionalDouble error = coProcessor.getError();
    OptionalDouble distance = coProcessor.getDistance();
    SmartDashboard.putString("Boiler Error", error.isPresent() ? Double.toString(error.getAsDouble()) : "NONE"); // don't use this number for real stuff
    SmartDashboard.putString("Boiler Distance", distance.isPresent() ? Double.toString(distance.getAsDouble()) : "NONE");
    Scheduler.getInstance().run();

//    SmartDashboard.putNumber("Shooter Left Speed", HardwareMap.TwinShooterMap.masterLeft.getSpeed());
//    SmartDashboard.putNumber("Shooter Right Speed", HardwareMap.TwinShooterMap.masterRight.getSpeed());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }
}
