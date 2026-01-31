package frc.robot.Subsystems.Climber;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private final ClimberIO io;
    private boolean on;
    private boolean climbing_state;

    private final static double initial_hook_pos = 0; // TODO: FIND 
    private final static double climbing_angle = 0;
    private final static double initial_tilt_angle = 0; // TODO: find  these
    private final static double CLIMBING_SPEED = 0.5;

 public Climber(String name, ClimberIO io) {
    this.io = io;
    this.on = false;
    this.climbing_state = false;  
  }


/**
   * Periodic method called once per scheduler run. Updates sensor inputs and maintains position
   * when necessary.
   */
  @Override
  public void periodic() { 
  }

  public void change_movement_state() {
    if (on) {
        io.setPercentOut(0); on = false;
    }
    else {
      io.setPercentOut(CLIMBING_SPEED); on = true;
    }
  }

  public void change_tilt_state() {
    if (climbing_state) {
      io.hold(initial_tilt_angle);
      climbing_state = false;
    } else {
      io.hold(climbing_angle);
      climbing_state = true;
    }
  }

  public void reset_hooks() {
  }

  /** Stops the climber, setting the otput to zero and maintaining position. */
  public void stop() {
    io.stop();
  }
}