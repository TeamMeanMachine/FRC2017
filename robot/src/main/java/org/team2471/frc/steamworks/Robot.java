package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.DoNothingAuto;
import org.team2471.frc.steamworks.autonomousroutines.*;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ZeroGyroCommand;
import org.team2471.frc.steamworks.subsystems.*;
import org.team2471.frc.util.net.ClockServer;

import java.util.OptionalDouble;

public class Robot extends IterativeRobot {
  public static final boolean COMPETITION = false;

  public static DriverStation.Alliance alliance;

  public static UPBoard coProcessor;
  public static Drive drive;
  public static FuelIntake fuelIntake;
  public static Shooter shooter;
  public static GearIntake gearIntake;
  public static FuelFlap flap;
  public static LEDController ledController;
  public static HopperWalls walls;

  public static SendableChooser autoChooser;

  private double startTime = Timer.getFPGATimestamp();

  @SuppressWarnings("unchecked")
  @Override
  public void robotInit() {
    // SmartDashboard.getString("Robot", "Competition").equals("Competition");
    //SmartDashboard.putString("Robot", "Practice");
    // wait for alliance color
    DriverStation ds = DriverStation.getInstance();
    while (true) {
      alliance = ds.getAlliance();
      if (alliance != DriverStation.Alliance.Invalid) {
        break;
      }
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }
    }

    shooter = new Shooter();
    drive = new Drive();
    fuelIntake = new FuelIntake();
    gearIntake = new GearIntake();
    ledController = new LEDController();
    flap = new FuelFlap();
    walls = new HopperWalls();
    HardwareMap.init();

    coProcessor = new UPBoard();
    IOMap.init();

    autoChooser = new SendableChooser();
    autoChooser.addObject("Don't Move", new DoNothingAuto());
    autoChooser.addObject("40 KPA Forward", new ForwardFortyKPAAuto());
    autoChooser.addObject("40 KPA Backwards", new BackwardFortyKPAAuto2());
    autoChooser.addObject("Drive Eight Feet", new DriveEightFeet());
    autoChooser.addObject("Feeder Lift", new FeederLiftAuto());
    autoChooser.addObject("Boiler Lift", new BoilerLiftAuto());
    autoChooser.addObject("Middle lift", new DriveToMiddleLift());
    autoChooser.addObject("One Hundred point Auto", new OneHundredPointAuto());
    autoChooser.addObject("Just Shoot Auto", new AimCommand(0, SmartDashboard.getNumber("RPM0", 2400)));
    autoChooser.addObject("Short fuel and gear", new BoilerGearAuto());
    autoChooser.addObject("Gear plus ten fuel", new GearTenAuto());
    autoChooser.addObject("Middle Lift + 10", new CenterLiftPlusTen());
    autoChooser.addObject("Gear Coop", new GearTen());

    SmartDashboard.putData("AutoChooser", autoChooser);
    SmartDashboard.putData(new ZeroGyroCommand());
    drive.disableClimbing();
    ClockServer.spawnClockServer(5802);
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
  public void teleopPeriodic() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Drive Speed", drive.getSpeed());
    SmartDashboard.putNumber("Gear Sensor", HardwareMap.GearIntakeMap.gearSensor.getValue());

    SmartDashboard.putNumber("Gyro", HardwareMap.gyro.getAngle());


    OptionalDouble error = coProcessor.getError();
    OptionalDouble distance = coProcessor.getDistance();
    SmartDashboard.putString("Boiler Error", error.isPresent() ? Double.toString(error.getAsDouble()) : "NONE"); // don't use this number for real stuff
    SmartDashboard.putString("Boiler Distance", distance.isPresent() ? Double.toString(distance.getAsDouble()) : "NONE");
    Scheduler.getInstance().run();

//    SmartDashboard.putNumber("Shooter Left Speed", HardwareMap.TwinShooterMap.masterLeft.getSpeed());
//    SmartDashboard.putNumber("Shooter Right Speed", HardwareMap.TwinShooterMap.masterRight.getSpeed());
    double endTime = Timer.getFPGATimestamp();
    double dt = endTime - startTime;
    SmartDashboard.putBoolean("Coprocessor Connected", coProcessor.isConnected());
    SmartDashboard.putNumber("Latency Quotient", 1 / 20 / dt * 100);
  }

  @Override
  public void testInit() {
    HardwareMap.gyro.calibrate();
  }

  @Override
  public void testPeriodic() {
    startTime = Timer.getFPGATimestamp();
    LiveWindow.run();
  }
}
