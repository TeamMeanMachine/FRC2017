package org.team2471.frc.steamworks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;

public class HardwareMap {

    public static final class FuelIntakeMap {
      public static final CANTalon intakeMotor = new CANTalon(0);
      public static final CANTalon leftWindshieldMotor = new CANTalon(0);
      public static final CANTalon rightWindshieldMotor = new CANTalon(0);
      public static final Solenoid intakeSolenoid = new Solenoid(0);

    }
}
