package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.constants.Constants;
import com.ctre.phoenix6.controls.Follower;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  private TalonFX ClimberMotorLeader;
  private TalonFX ClimberMotorFollower;
  private DutyCycleOut request;
  private MotionMagicVoltage ClimberMotionMagicVoltage;

  public Climber() {
    ClimberMotorLeader = new TalonFX(Constants.ClimberConstants.ClimberMotorLeaderId);
    ClimberMotorFollower = new TalonFX(Constants.ClimberConstants.ClimberMotorFollowerId);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);

    ClimberMotionMagicVoltage = new MotionMagicVoltage(0);
  }

  public void configMotor() {
    // Configure the motor controllers with the desired settings
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();

    internalConfig.Slot0.kP = Constants.ClimberConstants.ClimberMotorkP;
    internalConfig.Slot0.kV = Constants.ClimberConstants.ClimberMotorkV;

    ClimberMotorLeader.getConfigurator().apply(internalConfig);
    ClimberMotorFollower.getConfigurator().apply(internalConfig);
    internalConfig.MotionMagic.MotionMagicCruiseVelocity = Constants.ClimberConstants.ClimberMotorCruiseVelocity;
    internalConfig.MotionMagic.MotionMagicAcceleration = Constants.ClimberConstants.ClimberMotorAcceleration; 

    internalConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    internalConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = Constants.ClimberConstants.ClimberMotorUpperLimit; 
    internalConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    internalConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = Constants.ClimberConstants.ClimberMotorLowerLimit;

    internalConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    internalConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    ClimberMotorLeader.getConfigurator().apply(internalConfig);
    ClimberMotorFollower.getConfigurator().apply(internalConfig);

    ClimberMotorFollower.setControl(new Follower(ClimberMotorLeader.getDeviceID(), MotorAlignmentValue.Opposed));

  }

  public void setPosition(double rotations) {
    //Moves the climber to the desired position using Motion Magic control mode
    ClimberMotorLeader.setControl(ClimberMotionMagicVoltage.withPosition(rotations));
  }

  public void stop() {
    // Stops the climber by setting the motor output to zero and maintaining position
    ClimberMotorLeader.stopMotor();
  }

  @Override
  public void periodic() {
  }
}