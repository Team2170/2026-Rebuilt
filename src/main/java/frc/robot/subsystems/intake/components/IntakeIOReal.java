package frc.robot.subsystems.intake.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.IntakeConstants;

public class IntakeIOReal implements IntakeIO{
    private TalonFX IntakeMotor;

    private DutyCycleOut request;

    public IntakeIOReal() {
        IntakeMotor = new TalonFX(IntakeConstants.IntakeMotorID);

        configMotors();

        request = new DutyCycleOut(0);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        IntakeMotor.getConfigurator().apply(internalConfig);

        //Placeholder values
        internalConfig.Slot0.kP = 0.5;
        internalConfig.Slot0.kV = 0.1;

        //Some of these values need to be changed
        internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.CurrentLimits.withStatorCurrentLimit(40);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(30);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);

        IntakeMotor.getConfigurator().apply(internalConfig);

    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.IntakeMotorTorqueCurrentAmps = IntakeMotor.getTorqueCurrent().getValueAsDouble();
        inputs.IntakeMotorVelocityRotPerSec = IntakeMotor.getVelocity().getValueAsDouble();
        inputs.IntakeMotorConnected = IntakeMotor.isConnected();
        inputs.IntakeMotorControlMode = IntakeMotor.getControlMode().getValue();
        inputs.IntakeMotorPositionError = IntakeMotor.getClosedLoopError().getValueAsDouble();
    }

    public void setIntakePower(double percent) {
        IntakeMotor.setControl(request.withOutput(percent));
    }

    public void stopIntake() {
        IntakeMotor.setControl(request.withOutput(0));
    }
    
}
