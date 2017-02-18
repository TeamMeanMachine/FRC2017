package org.team2471.frc.steamworks;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team2471.frc.lib.control.MeanMotorController;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;
import org.team2471.frc.steamworks.autonomouscommands.CircleTestAutonomous;
import org.team2471.frc.steamworks.autonomouscommands.CoOpHopper;
import org.team2471.frc.steamworks.autonomouscommands.CoOpHopperAuto;
import org.team2471.frc.steamworks.autonomouscommands.DoNothingAuto;
import org.team2471.frc.steamworks.autonomouscommands.DriveEightFeet;
import org.team2471.frc.steamworks.autonomouscommands.DriveToHopperAuto;
import org.team2471.frc.steamworks.autonomouscommands.DriveToLeftLift;
import org.team2471.frc.steamworks.autonomouscommands.DriveToLift;
import org.team2471.frc.steamworks.autonomouscommands.GearPlusFarHopper;
import org.team2471.frc.steamworks.autonomouscommands.OneHundredPointAuto;
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

  public static SendableChooser autoChooser;

  Command autonomousCommand;


  @SuppressWarnings("unchecked")
  @Override
  public void robotInit() {
    twinShooter = new TwinShooter();
    drive = new Drive();
    gearIntake = new GearIntake();
    fuelIntake = new FuelIntake();

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
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledPeriodic() {
  }
}
