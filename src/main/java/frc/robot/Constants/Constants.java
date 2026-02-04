package frc.robot.constants;

import org.dyn4j.geometry.Rotation;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {
  public static final class ClimberConstants {
    public final static Rotation2d CLIMBING_ANGLE = new Rotation2d(0); // TODO: Find
    public final static Rotation2d INITIAL_ANGLE = new Rotation2d(0); // TODO: Find
    public final static double CLIMBING_UP_SPEED = 0.5;
    public final static double CLIMBING_DOWN_SPEED = -0.5;
    public final static int CLIMB_UP = 0;
    public final static int CLIMB_DOWN = 1;
    public final static int INITIAL_TILT_STATE = 0;
    public final static int CLIMB_TILT_STATE = 1;
    public final static int CLIMB_MOTOR_ID = 0; // TODO: Determine ID
    public final static int TILT_MOTOR_ID = 0; // TODO: Determine ID
    public final static double TILTING_SPEED = 0.3;
    public final static double GEAR_RATIO = 125.00;
    /*
     * the rotation state of the climbing motor necessary to bring the robot up to L1 from the floor
     */
    public final static Rotation2d TOWER_L1 = new Rotation2d(); // TODO: Find
    /*
     * the rotation state of the climbing motor while it is on the floor (reset pos)
     */
    public final static Rotation2d TOWER_L0 = new Rotation2d(); // TODO: Find
  }

  public static final double loopPeriodSecs = 0.02;
    public static final Mode robotMode = RobotBase.isReal() ? Mode.REAL : Mode.SIM;
    public static final boolean tuningMode = false;

    public enum Mode {
        REAL,
        SIM
    }

    public static boolean disableHAL = false;

    public static void disableHAL() {
        disableHAL = true;
    }

}
