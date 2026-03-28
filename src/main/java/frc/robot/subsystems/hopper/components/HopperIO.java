package frc.robot.subsystems.hopper.components;

import org.littletonrobotics.junction.AutoLog;

import com.ctre.phoenix6.signals.ControlModeValue;

import frc.robot.subsystems.intake.components.IntakeIO.IntakeIOInputs;

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
    }

    /**
     * Updates the sensor inputs for the intake.
     *
     * @param inputs The ShooterIOInputs object to be updated.
     */
    public default void updateInputs(IntakeIOInputs inputs) {
    }

    public default void setHopperPower(double percent) {
    }

     /** Stops the shooter motor. */
     public default void stopHopper() {
     }
}
