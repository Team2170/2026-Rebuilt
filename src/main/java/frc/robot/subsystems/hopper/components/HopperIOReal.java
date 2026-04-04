package frc.robot.subsystems.hopper.components;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

import frc.robot.constants.Constants.HopperConstants;
import frc.robot.constants.Constants.ShooterConstants;

public class HopperIOReal implements HopperIO {
    private TalonFX MasterHopperMotor;
    private TalonFX FollowerHopperMotor;
    // private TalonFX IntakingMotor;
    public static final double INTAKE_MOTOR_RATIO = 12;
    public static final double HOPPER_MOTOR_RATIO = 1;
    private DutyCycleOut request;
    private PositionDutyCycle requestPosition;
    private MotionMagicVoltage hopperMotionMagicVoltage;

    public HopperIOReal() {
        MasterHopperMotor = new TalonFX(HopperConstants.MasterHopperMotorID);
        FollowerHopperMotor = new TalonFX(HopperConstants.FollowerHopperMotorID);
        // IntakingMotor = new TalonFX(HopperConstants.IntakingMotorID);
        configMotors();

        request = new DutyCycleOut(0);
        requestPosition = new PositionDutyCycle(1);

        hopperMotionMagicVoltage = new MotionMagicVoltage(0);
    }

    public void configMotors() {
        
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        MasterHopperMotor.getConfigurator().apply(internalConfig);
        FollowerHopperMotor.getConfigurator().apply(internalConfig);
        // IntakingMotor.getConfigurator().apply(internalConfig);

        //Change these placeholders
        internalConfig.Slot0.kP = 0.5;
        internalConfig.Slot0.kV = 0.1;

        //Also change these placeholders
        internalConfig.MotionMagic.MotionMagicAcceleration = 0;
        internalConfig.MotionMagic.MotionMagicCruiseVelocity = 0;

        internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.Feedback.withSensorToMechanismRatio(HOPPER_MOTOR_RATIO);
        internalConfig.CurrentLimits.withStatorCurrentLimit(20);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(30);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = -0.5;
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -15; 
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        MasterHopperMotor.getConfigurator().apply(internalConfig);
        FollowerHopperMotor.getConfigurator().apply(internalConfig);
        FollowerHopperMotor.setControl(new Follower(MasterHopperMotor.getDeviceID(), MotorAlignmentValue.Opposed));
        internalConfig = new TalonFXConfiguration();

        internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
        internalConfig.Feedback.withSensorToMechanismRatio(INTAKE_MOTOR_RATIO);
        internalConfig.CurrentLimits.withStatorCurrentLimit(35);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(25);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);

        // IntakingMotor.getConfigurator().apply(internalConfig);
        
        
    }

    public void updateInputs(HopperIOInputs inputs) {
        inputs.HopperPosition = MasterHopperMotor.getPosition().getValueAsDouble();

        inputs.MasterHopperMotorTorqueCurrentAmps = MasterHopperMotor.getTorqueCurrent().getValueAsDouble();
        inputs.MasterHopperMotorVelocityRotPerSec = MasterHopperMotor.getVelocity().getValueAsDouble();
        inputs.MasterHopperMotorConnected = MasterHopperMotor.isConnected();
        inputs.MasterHopperMotorControlMode = MasterHopperMotor.getControlMode().getValue();
        inputs.MasterHopperMotorPositionError = MasterHopperMotor.getClosedLoopError().getValueAsDouble();

        inputs.FollowerHopperMotorTorqueCurrentAmps = FollowerHopperMotor.getTorqueCurrent().getValueAsDouble();
        inputs.FollowerHopperMotorVelocityRotPerSec = FollowerHopperMotor.getVelocity().getValueAsDouble();
        inputs.FollowerHopperMotorConnected = FollowerHopperMotor.isConnected();
        inputs.FollowerHopperMotorControlMode = FollowerHopperMotor.getControlMode().getValue();
        inputs.FollowerHopperMotorPositionError = FollowerHopperMotor.getClosedLoopError().getValueAsDouble();
    }


    //All these might not be necessary if the hopper motor just follows the shooter motor


    public void setHopperPower(double percent) {
        MasterHopperMotor.setControl(request.withOutput(percent));
    }

    // public void setIntakePower(double percent) {
    //     IntakingMotor.setControl(request.withOutput(percent));
    // }

    public void extendHopper() {
        MasterHopperMotor.setControl(requestPosition.withPosition(-15));
    }

    public void retractHopper() {
        MasterHopperMotor.setControl(requestPosition.withPosition(-0.5));
    }

    // public void stopIntake() {
    //     IntakingMotor.setControl(request.withOutput(0));
    // }

    public void stopHopper() {
        MasterHopperMotor.setControl(request.withOutput(0));
    }
}
