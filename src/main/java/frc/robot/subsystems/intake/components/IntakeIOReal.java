package frc.robot.subsystems.intake.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.IntakeConstants;
import frc.robot.subsystems.intake.components.IntakeIO.IntakeIOInputs;

public class IntakeIOReal {
    private TalonFX IntakingMotor;
    private TalonFX LiftMotor;
    public static final double INTAKE_MOTOR_RATIO = 1;
    public static final double LIFT_GEAR_RATIO = 12;
    private VelocityVoltage request;

    public IntakeIOReal() {
        IntakingMotor = new TalonFX(IntakeConstants.IntakingMotorID);
        LiftMotor = new TalonFX(IntakeConstants.IntakeLiftMotorID);
        configMotors();
        request = new VelocityVoltage(0).withEnableFOC(true);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        IntakingMotor.getConfigurator().apply(internalConfig);
        LiftMotor.getConfigurator().apply(internalConfig);

        internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
        internalConfig.Feedback.withSensorToMechanismRatio(INTAKE_MOTOR_RATIO);
        internalConfig.CurrentLimits.withStatorCurrentLimit(120);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);

        IntakingMotor.getConfigurator().apply(internalConfig);

        internalConfig.Feedback.withSensorToMechanismRatio(LIFT_GEAR_RATIO);

        LiftMotor.getConfigurator().apply(internalConfig);
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.IntakingMotorTorqueCurrentAmps = IntakingMotor.getTorqueCurrent().getValueAsDouble();
        inputs.IntakingMotorVelocityRotPerSec = IntakingMotor.getVelocity().getValueAsDouble();
        inputs.IntakingMotorMotorConnected = IntakingMotor.isConnected();
        inputs.IntakingMotorControlMode = IntakingMotor.getControlMode().getValue();
        inputs.IntakingMotorPositionError = IntakingMotor.getClosedLoopError().getValueAsDouble();

        inputs.LiftMotorTorqueCurrentAmps = LiftMotor.getTorqueCurrent().getValueAsDouble();
        inputs.LiftMotorVelocityRotPerSec = LiftMotor.getVelocity().getValueAsDouble();
        inputs.LiftMotorMotorConnected = LiftMotor.isConnected();
        inputs.LiftMotorControlMode = LiftMotor.getControlMode().getValue();
        inputs.LiftMotorPositionError = LiftMotor.getClosedLoopError().getValueAsDouble();
    }

    
}
