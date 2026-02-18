package frc.robot.subsystems.shooter.components;

import com.ctre.phoenix6.signals.ControlModeValue;
import org.littletonrobotics.junction.AutoLog;

/** Interface for the Climber subsystem's input/output operations. */
public interface ShooterIO {
  /**
   * Sets the climber motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public default void setPercentOut(double percent) {}

  /** Stops the climber motor. */
  public default void stop() {}
}