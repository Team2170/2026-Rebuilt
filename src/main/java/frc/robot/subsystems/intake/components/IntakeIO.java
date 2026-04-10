package frc.robot.subsystems.intake.components;

import org.littletonrobotics.junction.AutoLog;

import com.ctre.phoenix6.signals.ControlModeValue;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double IntakeMotorTorqueCurrentAmps = -1;
        public double IntakeMotorVelocityRotPerSec = -1;
        public boolean IntakeMotorConnected = false;
        public ControlModeValue IntakeMotorControlMode = ControlModeValue.DisabledOutput;
        public double IntakeMotorPositionError = -1;

    }

    public default void updateInputs(IntakeIOInputs inputs) {
    }

    public default void setIntakePower(double percent) {
    }

    public default void stopIntake() {
    }

}
