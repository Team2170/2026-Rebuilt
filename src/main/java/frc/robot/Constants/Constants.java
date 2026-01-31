package frc.robot.Constants;

import edu.wpi.first.math.geometry.Rotation2d;

public final class Constants {
  public static final class ClimberConstants {
    public final static Rotation2d CLIMBING_ANGLE = new Rotation2d(0); // TODO: CHANGE
    public final static Rotation2d INITIAL_ANGLE = new Rotation2d(0); // TODO: CHANGE
    public final static double CLIMBING_UP_SPEED = 0.5;
    public final static double CLIMBING_DOWN_SPEED = -0.5;
    public final static int CLIMB_UP = 0;
    public final static int CLIMB_DOWN = 1;
    public final static int INITIAL_TILT_STATE = 0;
    public final static int CLIMB_TILT_STATE = 1;
    public final static int CLIMB_MOTOR_ID = 0; // TODO: CHANGE
    public final static int TILT_MOTOR_ID = 0; // TODO: CHANGE
    public final static double TILTING_SPEED = 0.3;
    public final static double GEAR_RATIO = 125.00;
  }

}
