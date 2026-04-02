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
    private TalonFX IntakeRollerMotor;
    private TalonFX MasterIntakeMotor;
    private TalonFX FollowerIntakeMotor;

    private DutyCycleOut request;
    private PositionDutyCycle requestPosition;

    public IntakeIOReal() {
        IntakeRollerMotor = new TalonFX(IntakeConstants.IntakeRollerMotorID);
        MasterIntakeMotor = new TalonFX(IntakeConstants.MasterIntakeMotorID);
        FollowerIntakeMotor = new TalonFX(IntakeConstants.FollowerIntakeMotorID);

        configMotors();

        request = new DutyCycleOut(0);
        requestPosition = new PositionDutyCycle(1);
    }

    public void configMotors() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();
        IntakeRollerMotor.getConfigurator().apply(internalConfig);
        MasterIntakeMotor.getConfigurator().apply(internalConfig);
        FollowerIntakeMotor.getConfigurator().apply(internalConfig);

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

        IntakeRollerMotor.getConfigurator().apply(internalConfig);
        MasterIntakeMotor.getConfigurator().apply(internalConfig);
        FollowerIntakeMotor.getConfigurator().apply(internalConfig);
        FollowerIntakeMotor.setControl(new Follower(MasterIntakeMotor.getDeviceID(), MotorAlignmentValue.Opposed));

    }

    public void setIntakeRollerPower(double percent) {
        IntakeRollerMotor.setControl(request.withOutput(percent));
    }

    public void stopIntakeRoller() {
        IntakeRollerMotor.setControl(request.withOutput(0));
    }

    //Values need to be changed when the robot can be tested
    public void extendIntake() {
        MasterIntakeMotor.setControl(requestPosition.withPosition(0));
    }

    //Values need to be changed when the robot can be tested
    public void retractIntake() {
        MasterIntakeMotor.setControl(requestPosition.withPosition(0));
    }
    
}
