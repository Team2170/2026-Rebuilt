package frc.robot.Subsystems.Climber;

import com.ctre.phoenix6.signals.ControlModeValue;

import edu.wpi.first.math.geometry.Rotation2d;

import org.littletonrobotics.junction.AutoLog;

/** Interface for the Climber subsystem's input/output operations. */
public interface ClimberIO {

  /** Class representing input data for the Climber subsystem. */
  @AutoLog
  public static class ClimberIOInputs {
    // LEFT INPUTS
    public double ClimberTorqueCurrentAmps = -1;
    public double ClimberVelocityRotPerSec = -1;
    public boolean ClimberMotorConnected = false;
    public ControlModeValue ClimberControlMode = ControlModeValue.DisabledOutput;
    public double ClimberPositionError = -1;
    public double TilterTorqueCurrentAmps = -1;
    public double TilterVelocityRotPerSec = -1;
    public boolean TilterMotorConnected = false;
    public ControlModeValue TilterControlMode = ControlModeValue.DisabledOutput;
    public double TilterPositionError = -1;
  }

  /**
   * Updates the sensor inputs for the climber.
   *
   * @param inputs The ClimberIOInputs object to be updated.
   */
  public default void updateInputs(ClimberIOInputs inputs) {}

  /**
   * Sets the climber motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public default void set_climb_percent_out(double percent) {System.out.println("Percent set to " + percent);}

  public void set_tilt_state(Rotation2d rot);

  /**
   * Holds the climber at a specified position.
   *
   * @param rot The target position in rotations.
   */
  public default void hold(double rot) {}

}