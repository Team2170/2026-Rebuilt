package frc.robot.subsystems.hopper.components;

import org.littletonrobotics.junction.AutoLog;

import com.ctre.phoenix6.signals.ControlModeValue;

public interface HopperIO {
    @AutoLog
    public static class HopperIOInputs {
        public double HopperPosition = 1;

        public double MasterHopperMotorTorqueCurrentAmps = -1;
        public double MasterHopperMotorVelocityRotPerSec = -1;
        public boolean MasterHopperMotorConnected = false;
        public ControlModeValue MasterHopperMotorControlMode = ControlModeValue.DisabledOutput;
        public double MasterHopperMotorPositionError = -1;

        public double FollowerHopperMotorTorqueCurrentAmps = -1;
        public double FollowerHopperMotorVelocityRotPerSec = -1;
        public boolean FollowerHopperMotorConnected = false;
        public ControlModeValue FollowerHopperMotorControlMode = ControlModeValue.DisabledOutput;
        public double FollowerHopperMotorPositionError = -1;

        public double IntakingMotorPosition = -1;
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
    public default void updateInputs(HopperIOInputs inputs) {
    }

    // TODO Power or position better?
    public default void setHopperPower(double percent) {
    }

    /**
     * Sets the intake motor output as a percentage of total power.
     *
     * @param percent The desired power percentage (between -1.0 and 1.0).
     */
    // public default void setIntakePower(double percent) {
    // }

    /** Stops the shooter motor. */
    public default void stopHopper() {
    }

    public default void extendHopper() {
    }

    public default void retractHopper() {
    }
    // public default void stopIntake() {
    // }
}
