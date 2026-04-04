package frc.robot.subsystems.intake.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.IntakeConstants;

public class IntakeIOReal {
    private TalonFX IntakeMotor;

    private DutyCycleOut request;
    private PositionDutyCycle requestPosition;

    public IntakeIOReal() {
        IntakeMotor = new TalonFX(IntakeConstants.IntakeRollerMotorID);

        configMotors();

        request = new DutyCycleOut(0);
        requestPosition = new PositionDutyCycle(1);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        IntakeMotor.getConfigurator().apply(internalConfig);

        //Placeholder values
        internalConfig.Slot0.kP = 0.5;
        internalConfig.Slot0.kV = 0.1;
        
        //Placeholder values
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        //Some of these values need to be changed
        internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.CurrentLimits.withStatorCurrentLimit(40);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(30);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = -0.5;
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -15; 
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        IntakeMotor.getConfigurator().apply(internalConfig);

    }

    public void setIntakeRollerPower(double percent) {
        IntakeMotor.setControl(request.withOutput(percent));
    }

    public void stopIntakeRoller() {
        IntakeMotor.setControl(request.withOutput(0));
    }
    
}
