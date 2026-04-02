package frc.robot.constants;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;
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
        public static final int ShooterFeedMotorId = 19;

        public static final double FeedMotorRPS = 30;
    }

    public static class HopperConstants {
        public static final int MasterHopperMotorID = 14;
        public static final int FollowerHopperMotorID = 13;
        public static final int IntakingMotorID = 12;
    }

    public static class IntakeConstants {
        //Change these placeholders
        public static final int MasterIntakeMotorID = 0;
        public static final int FollowerIntakeMotorID = 0;
        public static final int IntakeRollerMotorID = 0;

    }

    public static class VisionConstants {
        public static AprilTagFieldLayout aprilTagLayout = AprilTagFieldLayout
                .loadField(AprilTagFields.k2026RebuiltWelded);

        public static final int degreesOffset = 15;

        public static final InterpolatingDoubleTreeMap Distance_To_RPS = new InterpolatingDoubleTreeMap();
        static {
            Distance_To_RPS.put(Units.inchesToMeters(40d), 40d);
            Distance_To_RPS.put(Units.inchesToMeters(60d), 45d);
            Distance_To_RPS.put(Units.inchesToMeters(80d), 50d);
            Distance_To_RPS.put(Units.inchesToMeters(100d), 55d);
            Distance_To_RPS.put(Units.inchesToMeters(120d), 60d);
        }

        public static final double Tag_Height_On_Tower = Units.inchesToMeters(44.25);
        public static final double Camera_Height_Meters = Units.inchesToMeters(7.5);

        // Camera names, must match names configured on coprocessor
        public static final String rightCameraName = "limelight-right";
        public static final String leftCameraName = "limelight-left";

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
