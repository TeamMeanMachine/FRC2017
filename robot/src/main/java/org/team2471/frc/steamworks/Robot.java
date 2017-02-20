package org.team2471.frc.steamworks;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.steamworks.autonomouscommands.*;
import org.team2471.frc.steamworks.comm.CoProcessor;
import org.team2471.frc.steamworks.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
  public static CoProcessor coProcessor;
  public static Drive drive;
  public static GearIntake gearIntake;
  public static FuelIntake fuelIntake;
  public static TwinShooter twinShooter;
  public static LEDController ledController;

  public static SendableChooser autoChooser;

  Command autonomousCommand;


  @SuppressWarnings("unchecked")
  @Override
  public void robotInit() {
    NetworkTable nt = NetworkTable.getTable("SmartDashboard");
    nt.getKeys().forEach(nt::clearPersistent);
    twinShooter = new TwinShooter();
    drive = new Drive();
    gearIntake = new GearIntake();
    fuelIntake = new FuelIntake();
    ledController = new LEDController();

    coProcessor = new CoProcessor();
    IOMap.init();

    autoChooser = new SendableChooser();
    autoChooser.addObject("Don't Move", new DoNothingAuto());
    autoChooser.addObject("Drive to Hopper", new DriveToHopperAuto());
    autoChooser.addObject("Drive Eight Feet", new DriveEightFeet(1.0));
    autoChooser.addObject("Drive to left Lift", new DriveToLeftLift(1.0));
    autoChooser.addObject("Drive to right Lift", new DriveToLeftLift(1.0, true));
    autoChooser.addObject("Drive to middle lift", new DriveToLift(1.0));
    autoChooser.addObject("One Hundred point Auto", new OneHundredPointAuto());
    autoChooser.addObject("Drop off gear and go to far Hopper", new GearPlusFarHopper());
    autoChooser.addObject("Circle Auto", new CircleTestAutonomous(1.0));
    autoChooser.addObject("CoOp Hopper", new CoOpHopper());
    autoChooser.addObject("One Hundred point Auto, Fuel first", new SecondOneHundredPointAuto());
    autoChooser.addObject("Backwards test", new DriveBackwardsFromLLToHopper(1.0,false));

    SmartDashboard.putData("AutoChooser", autoChooser);
  }

    @Override
    public void autonomousInit() {
      if (autoChooser != null) {
        autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
          autonomousCommand.start();
        }
      }
    }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Drive Speed", drive.getSpeed());
    SmartDashboard.putNumber("Gear Sensor", HardwareMap.GearIntakeMap.gearSensor.getValue());
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testPeriodic() {

  }
}
