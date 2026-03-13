package frc.robot.constants;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
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

    public static boolean disableHAL = false;

    public static void disableHAL() {
        disableHAL = true;
    }

    public static class ShooterConstants {
        public static final int ShooterBackMasterMotorId = 15;
        public static final int ShooterBackFollowerMotorId = 16;
        public static final int ShooterFrontMotorId = 19;

        public static final double FeedMotorRPS = 30;
    }

    public static class IntakeConstants {
        public static final int IntakeLiftMotorID = 18;
        public static final int IntakingMotorID = 17;
    }

    public static class VisionConstants {
        public static AprilTagFieldLayout aprilTagLayout = AprilTagFieldLayout
                .loadField(AprilTagFields.k2026RebuiltWelded);

        public static int degreesOffset = 15;

        // Camera names, must match names configured on coprocessor
        public static String rightCameraName = "limelight-right";
        public static String leftCameraName = "limelight-left";

        // Basic filtering thresholds
        public static double maxAmbiguity = 0.3;
        public static double maxZError = 0.75;

        // Standard deviation baselines, for 1 meter distance and 1 tag
        // (Adjusted automatically based on distance and # of tags)
        public static double linearStdDevBaseline = 0.5; // Meters
        public static double angularStdDevBaseline = 9999999; // Radians

        // Standard deviation multipliers for each camera
        // (Adjust to trust some cameras more than others)
        public static double[] cameraStdDevFactors = new double[] {
                1.0, // Camera 0
                1.0 // Camera 1
        };

        // Multipliers to apply for MegaTag 2 observations
        public static double linearStdDevMegatag2Factor = 0.5; // More stable than full 3D solve
        public static double angularStdDevMegatag2Factor = Double.POSITIVE_INFINITY; // No rotation data available
    }
}
