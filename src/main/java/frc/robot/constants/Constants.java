package frc.robot.constants;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
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

    public static class VisionConstants {
        public static AprilTagFieldLayout aprilTagLayout = AprilTagFieldLayout
                .loadField(AprilTagFields.k2026RebuiltWelded);

        // Camera names, must match names configured on coprocessor
        public static String camera0Name = "camera_0";
        public static String camera1Name = "camera_1";

        // Robot to camera transforms
        // (Not used by Limelight, configure in web UI instead)
        public static Transform3d robotToCamera0 = new Transform3d(0.2, 0.0, 0.2, new Rotation3d(0.0, -0.4, 0.0));
        public static Transform3d robotToCamera1 = new Transform3d(-0.2, 0.0, 0.2, new Rotation3d(0.0, -0.4, Math.PI));

        // Basic filtering thresholds
        public static double maxAmbiguity = 0.3;
        public static double maxZError = 0.75;

        // Standard deviation baselines, for 1 meter distance and 1 tag
        // (Adjusted automatically based on distance and # of tags)
        public static double linearStdDevBaseline = 0.02; // Meters
        public static double angularStdDevBaseline = 0.06; // Radians

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
