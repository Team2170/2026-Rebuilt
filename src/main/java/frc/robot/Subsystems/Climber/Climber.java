package frc.robot.Subsystems.Climber;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final ClimberIO io;
  private boolean climbing_down;
  private boolean climbing_up;
  private int tilt_state;

  private final static Rotation2d CLIMBING_ANGLE = new Rotation2d(0);
  private final static Rotation2d INITIAL_ANGLE = new Rotation2d(0); // TODO: find these
  private final static double CLIMBING_UP_SPEED = 0.5;
  private final static double CLIMBING_DOWN_SPEED = -0.5;

  public final static int CLIMB_UP = 0;
  public final static int CLIMB_DOWN = 1;
  public final static int INITIAL_TILT_STATE = 0;
  public final static int CLIMB_TILT_STATE = 1;

  public Climber(String name, ClimberIO io) {
    this.io = io;
    this.climbing_down = false;
    this.climbing_up = false;
    this.tilt_state = INITIAL_TILT_STATE;
  }

  /**
   * Periodic method called once per scheduler run. Updates sensor inputs and
   * maintains position
   * when necessary.
   */
  @Override
  public void periodic() {
  }

  /**
   * changes the climb state depending on the input given. options are
   * <code>Climber.CLIMB_UP</code> or <code>Climber.CLIMB_DOWN</code>. giving the
   * same climb command as the one that is active will make the climber stop.
   * giving the opposite command will immediately make it go to the opposite
   * command.
   * 
   * @param state desired climb state
   */
  public void change_climb_state(int state) {
    if (state == CLIMB_UP) {
      if (climbing_up) {
        io.set_climb_percent_out(0);
        climbing_up = false;
      } else if (climbing_down) {
        io.set_climb_percent_out(CLIMBING_UP_SPEED);
        climbing_down = false;
        climbing_up = true;
      } else {
        io.set_climb_percent_out(CLIMBING_UP_SPEED);
        climbing_up = true;
      }
    } else if (state == CLIMB_DOWN) {
      if (climbing_down) {
        io.set_climb_percent_out(0);
        climbing_down = false;
      } else if (climbing_up) {
        io.set_climb_percent_out(CLIMBING_DOWN_SPEED);
        climbing_down = true;
        climbing_up = false;
      } else {
        io.set_climb_percent_out(CLIMBING_DOWN_SPEED);
        climbing_down = true;
      }
    }
  }

  /**
   * changes the tilt state of the climber to prepare for climb. possible states
   * are <code>Climber.INITIAL_TILT_STATE</code> and
   * <code>Climber.CLIMB_TILT_STATE</code>
   * @param state desired tilt state
   */
  public void change_tilt_state(int state) {
    if (state == INITIAL_TILT_STATE) {
      if (tilt_state == CLIMB_TILT_STATE) {
        io.set_tilt_state(INITIAL_ANGLE);
        tilt_state = INITIAL_TILT_STATE;
      }
    } else if (state == CLIMB_TILT_STATE) {
      if (tilt_state == INITIAL_TILT_STATE) {
        io.set_tilt_state(CLIMBING_ANGLE);
        tilt_state = CLIMB_TILT_STATE;
      }
    }
  }
}