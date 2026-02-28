package frc.robot.subsystems.shooter.components;

import org.littletonrobotics.junction.AutoLog;

import com.ctre.phoenix6.signals.ControlModeValue;

/** Interface for the Shooter subsystem's input/output operations. */
public interface ShooterIO {
  @AutoLog
  public static class ShooterIOInputs {
    public double FrontMotorTorqueCurrentAmps = -1;
    public double FrontMotorVelocityRotPerSec = -1;
    public boolean FrontMotorMotorConnected = false;
    public ControlModeValue FrontMotorControlMode = ControlModeValue.DisabledOutput;
    public double FrontMotorPositionError = -1;

    public double BackMasterMotorTorqueCurrentAmps = -1;
    public double BackMasterMotorVelocityRotPerSec = -1;
    public boolean BackMasterMotorMotorConnected = false;
    public ControlModeValue BackMasterMotorControlMode = ControlModeValue.DisabledOutput;
    public double BackMasterMotorPositionError = -1;
    
    public double BackFollowerMotorTorqueCurrentAmps = -1;
    public double BackFollowerMotorVelocityRotPerSec = -1;
    public boolean BackFollowerMotorMotorConnected = false;
    public ControlModeValue BackFollowerMotorControlMode = ControlModeValue.DisabledOutput;
    public double BackFollowerMotorPositionError = -1;
  }

  /**
   * Updates the sensor inputs for the shooter.
   *
   * @param inputs The ShooterIOInputs object to be updated.
   */
  public default void updateInputs(ShooterIOInputs inputs) {
  }

  /**
   * Sets the shooter motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the shooter motor (-1.0 to 1.0).
   */
  public default void setPercentOut(double percent) {
  }

  /** Stops the shooter motor. */
  public default void stop() {
  }
}