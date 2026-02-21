package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
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

        public static final double SHOOTING_SPEED = 0.6;
    }

    public static final class ClimberConstants {
        /*
         * distance from floor to level 1 of tower. in inches
         */
        private final static int FLOOR_TO_L1 = 27;
        /*
         * distance from level 1 of tower to level 2 of tower. in inches.
         */
        private final static int L1_TO_L2 = 18;
        /*
         * distance from level 2 of tower to level 3 of tower. in inches.
         */
        private final static int L2_TO_L3 = 18;
        public final static Rotation2d CLIMBING_ANGLE = new Rotation2d(0); // TODO: Find
        public final static double CLIMBING_UP_SPEED = 0.5;
        public final static double CLIMBING_DOWN_SPEED = -0.5;
        public final static int CLIMB_UP = 0;
        public final static int CLIMB_DOWN = 1;
        public final static int CLIMB_MOTOR_ID = 0; // TODO: Determine ID
        public final static double GEAR_RATIO = 125.00;
        public final static Rotation2d TOWER_L1 = new Rotation2d(); // TODO: find
        public final static Rotation2d TOWER_L0 = new Rotation2d(); // TODO: find. find the most efficient way to get
                                                                    // here, so we dont have to undo all actions done
                                                                    // while climbing
    }
}
