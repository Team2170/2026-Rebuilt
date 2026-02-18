package frc.robot.subsystems.shooter.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.constants.Constants;

/** Implementation of ClimberIO for real hardware, using a TalonFX motor controller. */
public class ShooterIOReal implements ShooterIO {
  private TalonFX BackMasterMotor;
  private TalonFX BackFollowerMotor;
  private TalonFX FrontMotor;
  public static final double BACK_GEAR_RATIO = 1.00; // SET THIS UP
  public static final double FRONT_GEAR_Ratio = 3;
  private DutyCycleOut request;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration parameters.
   */
  public ShooterIOReal() {
    BackMasterMotor = new TalonFX(Constants.ShooterConstants.ShooterBackMasterMotorId);
    BackFollowerMotor = new TalonFX(Constants.ShooterConstants.ShooterBackFollowerMotorId);
    FrontMotor = new TalonFX(Constants.ShooterConstants.ShooterFrontMotorId);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
  }

  /** Configures the motor with the provided parameters. */
  public void configMotor() {
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();
    BackFollowerMotor.getConfigurator().apply(internalConfig);
    BackMasterMotor.getConfigurator().apply(internalConfig);

    internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    internalConfig.Feedback.withSensorToMechanismRatio(BACK_GEAR_RATIO);
    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    BackMasterMotor.getConfigurator().apply(internalConfig);
    BackFollowerMotor.getConfigurator().apply(internalConfig);

    internalConfig.Feedback.withSensorToMechanismRatio(FRONT_GEAR_Ratio);
    FrontMotor.getConfigurator().apply(internalConfig);
  }

  /**
   * Sets the motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public void setPercentOut(double percent) {
    BackMasterMotor.setControl(request.withOutput(percent));
    BackFollowerMotor.setControl(request.withOutput(percent));

    FrontMotor.setControl(request.withOutput(0.6));
  }

  /** Stops the climber motor. */
  public void stop() {
    BackFollowerMotor.stopMotor();
    BackMasterMotor.stopMotor();
    FrontMotor.stopMotor();
  }
}