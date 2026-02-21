package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

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

    public static class HopperConstants{
        // TODO: Set these to the correct values
        public static final int HopperMotorId = 4;
    }
}
