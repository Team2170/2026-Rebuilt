package frc.robot.constants;

import org.dyn4j.geometry.Rotation;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {
  public static final class ClimberConstants {
    /*
     * CTC for center-to-center. center to center chain length represents the length
     * of the chain from the center of the bottom gear to the center of the top
     * gear. in inches.
     */
    private final static double CTC_CHAIN_LENGTH = 25;
    /*
     * radius of the gears that the chain is on. in inches.
     */
    private final static double GEAR_RADIUS = 1;
    /*
     * linear length of the chain. in inches.
     */
    private final static double CHAIN_LENGTH = 2 * CTC_CHAIN_LENGTH + 2 * Math.PI * GEAR_RADIUS;
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
     * the rotation state of the climbing motor necessary to bring the robot up to
     * L1 from the floor. 
     * 
     * NOTE: this assumes climbing hooks begin at h=0. this is not the case.
     */
    public final static Rotation2d TOWER_L1 = new Rotation2d(( FLOOR_TO_L1 / (GEAR_RADIUS * 2 * Math.PI)) * GEAR_RATIO);
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
