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
import org.team2471.frc.steamworks.autonomouscommands.DriveDistanceCommand;
import org.team2471.frc.steamworks.autonomousroutines.*;
import org.team2471.frc.steamworks.commands.AimCommand;
import org.team2471.frc.steamworks.commands.ZeroGyroCommand;
import org.team2471.frc.steamworks.subsystems.*;
import org.team2471.frc.util.commands.RunCommand;

public class Robot extends IterativeRobot {
  public static final boolean COMPETITION = true;
  public static final boolean SKIP_GYRO_CALIBRATION = true;

  public static DriverStation.Alliance alliance;

  public static Drive drive;
  public static FuelIntake fuelIntake;
  public static Shooter shooter;
  public static GearIntake gearIntake;
  public static FuelFlap flap;
  public static LEDController ledController;
  public static HopperWalls walls;

  public static CameraFeed feed;

  public static ChesDroid cheezDroid;

  public static SendableChooser<Command> autoChooser;

  private double startTime = Timer.getFPGATimestamp();

  @Override
  public void robotInit() {
    cheezDroid = new ChesDroid();

    if(!SKIP_GYRO_CALIBRATION) {
      System.out.println("Calibrating Gyro...");
      HardwareMap.gyro.calibrate();
      System.out.println("Gyro calibrated");
    }

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

    feed = new CameraFeed();

    IOMap.init();

    autoChooser = new SendableChooser<>();
    autoChooser.addObject("Hitler's Auto", new DoNothingAuto());
    autoChooser.addObject("40 KPA Forward", new ForwardFortyKPAAuto());
    autoChooser.addObject("40 KPA Backwards", new BackwardFortyKPAAuto());
    autoChooser.addObject("Drive Eight Feet", new DriveEightFeet());
    autoChooser.addObject("Feeder Lift", new FeederLiftAuto());
    autoChooser.addObject("Boiler Lift", new BoilerLiftAuto());
    autoChooser.addObject("Middle lift", new CenterLiftPlusTen());
    autoChooser.addObject("One Hundred point Auto", new OneHundredPointAuto());
    autoChooser.addObject("Just Shoot Auto", new AimCommand(0, SmartDashboard.getNumber("RPM1", 2471), 1.0, false));
    autoChooser.addObject("Short fuel and gear", new BoilerGearAuto());
    autoChooser.addObject("Gear plus ten fuel", new GearTenAuto());
    autoChooser.addObject("Middle Lift + 10", new CenterLiftPlusTen());
    autoChooser.addObject("Feeder Lift + 10", new FeederLiftPlusTen());
    autoChooser.addObject("Test Auto (don't run this drive team)", new DriveDistanceCommand(6.5, 2.2));

    SmartDashboard.putData("AutoChooser", autoChooser);
    SmartDashboard.putData(new ZeroGyroCommand());
    SmartDashboard.putData("Restart ADB", new RunCommand(cheezDroid::restartAdb));
    drive.disableClimbing();
  }

  @Override
  public void autonomousInit() {
    if (autoChooser != null) {
      Command autonomousCommand = autoChooser.getSelected();
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

    SmartDashboard.putString("Auto Setpoints", drive.getLeftMotor1().getSetpoint() + ":" + drive.getRightMotor1().getSetpoint());
    SmartDashboard.putString("Auto Errors", drive.getLeftMotor1().getError() + ":" + drive.getRightMotor1().getError()
      + ":" + drive.getLeftMotor1().getPosition() + ":" + drive.getRightMotor1().getPosition());
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Climber Current", Robot.fuelIntake.getCurrent());
    SmartDashboard.putNumber("Drive Speed", drive.getSpeed());
    SmartDashboard.putNumber("Gear Sensor", HardwareMap.GearIntakeMap.gearSensor.getValue());

    SmartDashboard.putNumber("Gyro", HardwareMap.gyro.getAngle());


    SmartDashboard.putNumber("Gear Intake Current", gearIntake.getCurrentDraw());
    Scheduler.getInstance().run();

//    SmartDashboard.putNumber("Shooter Left Speed", HardwareMap.TwinShooterMap.masterLeft.getSpeed());
//    SmartDashboard.putNumber("Shooter Right Speed", HardwareMap.TwinShooterMap.masterRight.getSpeed());
    double endTime = Timer.getFPGATimestamp();
    double dt = endTime - startTime;
    SmartDashboard.putBoolean("Coprocessor Connected", cheezDroid.isConnected());
    SmartDashboard.putBoolean("Coprocessor Received Packet", cheezDroid.hasReceivedPacket());
    SmartDashboard.putNumber("Latency Quotient", 1 / 20 / dt * 100);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    startTime = Timer.getFPGATimestamp();
    LiveWindow.run();
  }
}
