package frc.robot.subsystems.climber;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;

public class Climber extends SubsystemBase {
  private final ClimberIO io;
  private boolean climbing_down;
  private boolean climbing_up;
  private int tilt_state;

  public Climber(ClimberIO io) {
    this.io = io;
    this.climbing_down = false;
    this.climbing_up = false;
    this.tilt_state = Constants.ClimberConstants.INITIAL_TILT_STATE;
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
    if (state == Constants.ClimberConstants.CLIMBING_UP_SPEED) {
      if (climbing_up) {
        io.set_climb_percent_out(0);
        climbing_up = false;
      } else if (climbing_down) {
        io.set_climb_percent_out(Constants.ClimberConstants.CLIMBING_UP_SPEED);
        climbing_down = false;
        climbing_up = true;
      } else {
        io.set_climb_percent_out(Constants.ClimberConstants.CLIMBING_UP_SPEED);
        climbing_up = true;
      }
    } else if (state == Constants.ClimberConstants.CLIMBING_DOWN_SPEED) {
      if (climbing_down) {
        io.set_climb_percent_out(0);
        climbing_down = false;
      } else if (climbing_up) {
        io.set_climb_percent_out(Constants.ClimberConstants.CLIMBING_DOWN_SPEED);
        climbing_down = true;
        climbing_up = false;
      } else {
        io.set_climb_percent_out(Constants.ClimberConstants.CLIMBING_DOWN_SPEED);
        climbing_down = true;
      }
    }
  }

  /**
   * changes the tilt state of the climber to prepare for climb. possible states
   * are <code>Climber.INITIAL_TILT_STATE</code> and
   * <code>Climber.CLIMB_TILT_STATE</code>
   * 
   * @param state desired tilt state
   */
  public void change_tilt_state(int state) {
    if (state == Constants.ClimberConstants.INITIAL_TILT_STATE) {
      if (tilt_state == Constants.ClimberConstants.CLIMB_TILT_STATE) {
        io.set_tilt_state(Constants.ClimberConstants.INITIAL_ANGLE);
        tilt_state = Constants.ClimberConstants.INITIAL_TILT_STATE;
      }
    } else if (state == Constants.ClimberConstants.CLIMB_TILT_STATE) {
      if (tilt_state == Constants.ClimberConstants.INITIAL_TILT_STATE) {

        io.set_tilt_state(Constants.ClimberConstants.CLIMBING_ANGLE);
        tilt_state = Constants.ClimberConstants.CLIMB_TILT_STATE;
      }
    }
  }

  /**
   * resets the climbing motors back to their initial positions to prepare for another climb
   */
  public void reset() {
    io.set_climbing_state(Constants.ClimberConstants.TOWER_L0);
  }

  /**
   * moves the climbing motors until it is at l1
   */
  public void climb_L1() {
    io.set_climbing_state(Constants.ClimberConstants.TOWER_L1);
  }
}