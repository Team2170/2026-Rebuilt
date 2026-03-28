package frc.robot.subsystems.hopper.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.HopperConstants;

public class HopperIOReal {
    private TalonFX MasterHopperMotor;
    private TalonFX FollowerHopperMotor;
    public static final double HOPPER_MOTOR_RATIO = 1;
    private DutyCycleOut request;

    public HopperIOReal() {
        MasterHopperMotor = new TalonFX(HopperConstants.MasterHopperMotorID);
        FollowerHopperMotor = new TalonFX(HopperConstants.FollowerHopperMotorID);
        configMotors();

        request = new DutyCycleOut(0).withEnableFOC(true);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        MasterHopperMotor.getConfigurator().apply(internalConfig);
        FollowerHopperMotor.getConfigurator().apply(internalConfig);

        // TODO Setup
        internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.Feedback.withSensorToMechanismRatio(HOPPER_MOTOR_RATIO);
        internalConfig.CurrentLimits.withStatorCurrentLimit(40);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(30);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);  
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0.5; //TODO Setup
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -0.5; //TODO Setup
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        MasterHopperMotor.getConfigurator().apply(internalConfig);

        FollowerHopperMotor.setControl(new Follower(MasterHopperMotor.getDeviceID(), MotorAlignmentValue.Opposed)); //TODO Setup
		internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);

        
    }
}
