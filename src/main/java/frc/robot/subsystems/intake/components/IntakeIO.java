package frc.robot.subsystems.intake.components;

import org.littletonrobotics.junction.AutoLog;

import com.ctre.phoenix6.signals.ControlModeValue;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double LiftMotorTorqueCurrentAmps = -1;
        public double LiftMotorVelocityRotPerSec = -1;
        public boolean LiftMotorMotorConnected = false;
        public ControlModeValue LiftMotorControlMode = ControlModeValue.DisabledOutput;
        public double LiftMotorPositionError = -1;

        public double IntakingMotorTorqueCurrentAmps = -1;
        public double IntakingMotorVelocityRotPerSec = -1;
        public boolean IntakingMotorMotorConnected = false;
        public ControlModeValue IntakingMotorControlMode = ControlModeValue.DisabledOutput;
        public double IntakingMotorPositionError = -1;
    }

    /**
     * Updates the sensor inputs for the intake.
     *
     * @param inputs The ShooterIOInputs object to be updated.
     */
    public default void updateInputs(IntakeIOInputs inputs) {
    }

    /**
     * Sets the intake motor output as a percentage of total power.
     *
     * @param rps The rotational velocity output to set the intaking motor (RPS).
     */
    public default void setIntakeVelocity(double rps) {
    }

    /**
     * Sets the intake motor output as a percentage of total power.
     *
     * @param rps The rotational velocity output to set the intaking motor (RPS).
     */
    public default void setLiftVelocity(double rps) {
    }

    // public default void setPercentOut(double percent) {
    // }

    /** Stops the shooter motor. */
    public default void stop() {
    }
}
