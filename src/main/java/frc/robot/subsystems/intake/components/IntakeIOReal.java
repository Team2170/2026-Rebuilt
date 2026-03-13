package frc.robot.subsystems.intake.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.IntakeConstants;

public class IntakeIOReal implements IntakeIO {
    private TalonFX IntakingMotor;
    private TalonFX LiftMotor;
    public static final double INTAKE_MOTOR_RATIO = 1;
    public static final double LIFT_GEAR_RATIO = 12;
    private DutyCycleOut request;

    public IntakeIOReal() {
        IntakingMotor = new TalonFX(IntakeConstants.IntakingMotorID);
        LiftMotor = new TalonFX(IntakeConstants.IntakeLiftMotorID);
        configMotors();
        request = new DutyCycleOut(0).withEnableFOC(true);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        IntakingMotor.getConfigurator().apply(internalConfig);
        LiftMotor.getConfigurator().apply(internalConfig);

        internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
        internalConfig.Feedback.withSensorToMechanismRatio(INTAKE_MOTOR_RATIO);
        internalConfig.CurrentLimits.withStatorCurrentLimit(35);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);

        IntakingMotor.getConfigurator().apply(internalConfig);

        internalConfig.Feedback.withSensorToMechanismRatio(LIFT_GEAR_RATIO);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;    
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -0.5;

        LiftMotor.getConfigurator().apply(internalConfig);
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.IntakingMotorPosition = IntakingMotor.getPosition().getValueAsDouble();
        inputs.IntakingMotorTorqueCurrentAmps = IntakingMotor.getTorqueCurrent().getValueAsDouble();
        inputs.IntakingMotorVelocityRotPerSec = IntakingMotor.getVelocity().getValueAsDouble();
        inputs.IntakingMotorMotorConnected = IntakingMotor.isConnected();
        inputs.IntakingMotorControlMode = IntakingMotor.getControlMode().getValue();
        inputs.IntakingMotorPositionError = IntakingMotor.getClosedLoopError().getValueAsDouble();

        inputs.LiftMotorPosition = LiftMotor.getPosition().getValueAsDouble();
        inputs.LiftMotorTorqueCurrentAmps = LiftMotor.getTorqueCurrent().getValueAsDouble();
        inputs.LiftMotorVelocityRotPerSec = LiftMotor.getVelocity().getValueAsDouble();
        inputs.LiftMotorMotorConnected = LiftMotor.isConnected();
        inputs.LiftMotorControlMode = LiftMotor.getControlMode().getValue();
        inputs.LiftMotorPositionError = LiftMotor.getClosedLoopError().getValueAsDouble();
    }

    public void setIntakePower(double percent) {
        IntakingMotor.setControl(request.withOutput(percent));
    }

    public void setLiftPower(double percent) {
        LiftMotor.setControl(request.withOutput(percent));
    }

    public void stopIntake() {
        IntakingMotor.stopMotor();
    }

    public void stopLift() {
        LiftMotor.stopMotor();
    }
}
