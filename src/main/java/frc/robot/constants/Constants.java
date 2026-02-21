package frc.robot.constants;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
    public static final CANBus canbus = new CANBus("rio");

    public static enum Mode {
        /** Running on a real robot. */
        REAL,

        /** Running a physics simulator. */
        SIM,

        /** Replaying from a log file. */
        REPLAY
    }

    public static class ShooterConstants {
        // TODO: Set these to the correct values
        public static final int ShooterBackMasterMotorId = 1;
        public static final int ShooterBackFollowerMotorId = 2;
        public static final int ShooterFrontMotorId = 3;
    }

    public static class IntakeConstants{
       public static final int SpinMotorId = 0;//change
       public static final int LiftMotorId =0;//change
   }

}
