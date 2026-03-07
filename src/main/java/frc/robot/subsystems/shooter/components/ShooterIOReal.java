package frc.robot.subsystems.shooter.components;

import frc.robot.constants.Constants.ShooterConstants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

/**
 * Implementation of ClimberIO for real hardware, using a TalonFX motor
 * controller.
 */
public class ShooterIOReal implements ShooterIO {
  private TalonFX BackMasterMotor;
  private TalonFX BackFollowerMotor;
  private TalonFX FrontMotor;
  public static final double BACK_GEAR_RATIO = 1; 
  public static final double FRONT_GEAR_RATIO = 3;
  private VelocityVoltage request;
  // private DutyCycleOut request;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration
   *            parameters.
   */
  public ShooterIOReal() {
    BackMasterMotor = new TalonFX(ShooterConstants.ShooterBackMasterMotorId);
    BackFollowerMotor = new TalonFX(ShooterConstants.ShooterBackFollowerMotorId);
    FrontMotor = new TalonFX(ShooterConstants.ShooterFrontMotorId);
    configMotors();
    request = new VelocityVoltage(0).withEnableFOC(true);
    // request = new DutyCycleOut(0).withEnableFOC(true);
  }

  /** Configures the motor with the provided parameters. */
  public void configMotors() {
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();
    BackFollowerMotor.getConfigurator().apply(internalConfig);
    BackMasterMotor.getConfigurator().apply(internalConfig);

    internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    internalConfig.Feedback.withSensorToMechanismRatio(BACK_GEAR_RATIO);
    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    internalConfig.Slot0.kA = 0.001;
    internalConfig.Slot0.kD = 0.02;
    internalConfig.Slot0.kI = 0.001;
    internalConfig.Slot0.kP = 0.2;
    internalConfig.Slot0.kS = 0;
    internalConfig.Slot0.kV = 0.003;

    BackMasterMotor.getConfigurator().apply(internalConfig);
    BackFollowerMotor.getConfigurator().apply(internalConfig);
    BackFollowerMotor.setControl(new Follower(BackFollowerMotor.getDeviceID(), MotorAlignmentValue.Opposed));

    internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    internalConfig.Feedback.withSensorToMechanismRatio(FRONT_GEAR_RATIO);
    FrontMotor.getConfigurator().apply(internalConfig);
  }

  public void updateInputs(ShooterIOInputs inputs) {
    inputs.BackMasterMotorTorqueCurrentAmps = BackMasterMotor.getTorqueCurrent().getValueAsDouble();
    inputs.BackMasterMotorVelocityRotPerSec = BackMasterMotor.getVelocity().getValueAsDouble();
    inputs.BackMasterMotorMotorConnected = BackMasterMotor.isConnected();
    inputs.BackMasterMotorControlMode = BackMasterMotor.getControlMode().getValue();
    inputs.BackMasterMotorPositionError = BackMasterMotor.getClosedLoopError().getValueAsDouble();

    inputs.BackFollowerMotorTorqueCurrentAmps = BackFollowerMotor.getTorqueCurrent().getValueAsDouble();
    inputs.BackFollowerMotorVelocityRotPerSec = BackFollowerMotor.getVelocity().getValueAsDouble();
    inputs.BackFollowerMotorMotorConnected = BackFollowerMotor.isConnected();
    inputs.BackFollowerMotorControlMode = BackFollowerMotor.getControlMode().getValue();
    inputs.BackFollowerMotorPositionError = BackFollowerMotor.getClosedLoopError().getValueAsDouble();

    inputs.FrontMotorTorqueCurrentAmps = FrontMotor.getTorqueCurrent().getValueAsDouble();
    inputs.FrontMotorVelocityRotPerSec = FrontMotor.getVelocity().getValueAsDouble();
    inputs.FrontMotorMotorConnected = FrontMotor.isConnected();
    inputs.FrontMotorControlMode = FrontMotor.getControlMode().getValue();
    inputs.FrontMotorPositionError = FrontMotor.getClosedLoopError().getValueAsDouble();
  }

  /**
   * Sets the motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public void setVelocityOut(double rps) {
    BackMasterMotor.setControl(request.withVelocity(rps));
    BackFollowerMotor.setControl(request.withVelocity(rps));

    FrontMotor.setControl(request.withVelocity(30));
  }

  // public void setPercentOut(double percent) {
  //   BackMasterMotor.setControl(request.withOutput(percent));
  //   BackFollowerMotor.setControl(request.withOutput(percent));

  //   FrontMotor.setControl(request.withOutput(percent));
  // }

  /** Stops the shooter motor. */
  public void stop() {
    BackFollowerMotor.stopMotor();
    BackMasterMotor.stopMotor();
    FrontMotor.stopMotor();
  }
}