package frc.robot.subsystems.hopper.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.constants.Constants.HopperConstants;

public class HopperIOReal implements HopperIO {
    private TalonFX MasterHopperMotor;
    private TalonFX FollowerHopperMotor;
    private TalonFX IntakingMotor;
    public static final double INTAKE_MOTOR_RATIO = 3;
    public static final double HOPPER_MOTOR_RATIO = 1;
    private DutyCycleOut request;
    private VelocityVoltage velocityRequest;
    private MotionMagicVoltage requestPosition;

    public HopperIOReal() {
        MasterHopperMotor = new TalonFX(HopperConstants.MasterHopperMotorID);
        FollowerHopperMotor = new TalonFX(HopperConstants.FollowerHopperMotorID);
        IntakingMotor = new TalonFX(HopperConstants.IntakingMotorID);
        configMotors();

        request = new DutyCycleOut(0);
        velocityRequest = new VelocityVoltage(0);
        requestPosition = new MotionMagicVoltage(0);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        MasterHopperMotor.getConfigurator().apply(internalConfig);
        FollowerHopperMotor.getConfigurator().apply(internalConfig);
        IntakingMotor.getConfigurator().apply(internalConfig);

        internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);

        internalConfig.Feedback.withSensorToMechanismRatio(HOPPER_MOTOR_RATIO);

        internalConfig.CurrentLimits.withStatorCurrentLimit(40);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        internalConfig.CurrentLimits.withSupplyCurrentLimit(30);
        internalConfig.CurrentLimits.withSupplyCurrentLimitEnable(true);

        // TODO Figure out if limits are necessary for manual hopper in & out
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = -0.5;
        internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -19.5; 
        internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

        internalConfig.MotionMagic.withMotionMagicCruiseVelocity(5)
                .withMotionMagicAcceleration(10)
                .withMotionMagicJerk(50);

        internalConfig.Slot0.kP = 0; // tune these
        internalConfig.Slot0.kI = 0.0;
        internalConfig.Slot0.kD = 0.1;
        internalConfig.Slot0.kV = 0.12;
        internalConfig.Slot0.kS = 0;

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

        internalConfig.Slot0.kP = 0.2;
        internalConfig.Slot0.kI = 0.005;
        internalConfig.Slot0.kD = 0.03;
        internalConfig.Slot0.kV = 0.12;
        internalConfig.Slot0.kS = 0.24;

        IntakingMotor.getConfigurator().apply(internalConfig);
    }

    public void updateInputs(HopperIOInputs inputs) {
        inputs.HopperPosition = (MasterHopperMotor.getPosition().getValueAsDouble() + FollowerHopperMotor.getPosition().getValueAsDouble()) / 2.0;

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

        inputs.IntakingMotorControlMode = IntakingMotor.getControlMode().getValue();
        inputs.IntakingMotorPosition = IntakingMotor.getPosition().getValueAsDouble();
        inputs.IntakingMotorTorqueCurrentAmps = IntakingMotor.getTorqueCurrent().getValueAsDouble();
        inputs.IntakingMotorVelocityRotPerSec = IntakingMotor.getVelocity().getValueAsDouble();
        inputs.IntakingMotorMotorConnected = IntakingMotor.isConnected();
        inputs.IntakingMotorPositionError = IntakingMotor.getClosedLoopError().getValueAsDouble();
    }

    public void setHopperPower(double percent) {
        MasterHopperMotor.setControl(request.withOutput(percent));
    }

    public void setIntakeRPS(double rps) {
        IntakingMotor.setControl(velocityRequest.withVelocity(rps));
    }

    public void extendHopper() {
        MasterHopperMotor.setControl(requestPosition.withPosition(-18).withSlot(0));
    }

    public void retractHopper() {
        MasterHopperMotor.setControl(requestPosition.withPosition(-1).withSlot(0));
    }

    public void stopIntake() {
        IntakingMotor.setControl(request.withOutput(0));
    }

    public void stopHopper() {
        MasterHopperMotor.setControl(request.withOutput(0));
    }
}
