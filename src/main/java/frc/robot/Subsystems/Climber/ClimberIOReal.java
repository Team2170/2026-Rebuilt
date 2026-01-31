package frc.robot.Subsystems.Climber;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.Constants;

/**
 * Implementation of ClimberIO for real hardware, using a TalonFX motor
 * controller.
 */
public class ClimberIOReal implements ClimberIO {
  private TalonFX climbing_motor;
  private TalonFX tilting_motor;

  private final static Rotation2d CLIMBING_TILT_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d INITIAL_TILT_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d INITIAL_HOOK_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d L1_HOOK_STATE = new Rotation2d(0);
  private final static Rotation2d L2_HOOK_STATE = new Rotation2d(0);
  private final static Rotation2d L3_HOOK_STATE = new Rotation2d(0);

  private final static int tiltId = 1; // TODO: USE CONSTANTS CLASS
  private final static int climbId = 2;

  private static final double GEAR_RATIO = 125.00;
  private DutyCycleOut request;
  private PositionDutyCycle holdPosRequest;
  private final static double TILTING_SPEED = 0.3;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration
   *            parameters.
   */
  public ClimberIOReal() {
    climbing_motor = new TalonFX(climbId); // USE CONSTANTS CLASS LATER
    tilting_motor = new TalonFX(tiltId); // USE CONSTANTS CLASS LATER
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    holdPosRequest = new PositionDutyCycle(0);
    holdPosRequest.Velocity = TILTING_SPEED; // TODO: change later
  }

  /** Configures the motor with the provided parameters. */
  public void configMotor() {
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();

    internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    internalConfig.Feedback.withSensorToMechanismRatio(GEAR_RATIO);
    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    // Apply all settings.
    climbing_motor.getConfigurator().apply(internalConfig);
    tilting_motor.getConfigurator().apply(internalConfig);
  }

  /**
   * Updates the input state with the current sensor values.
   *
   * @param inputs The ClimberIOInputs object to update.
   */
  public void updateInputs(ClimberIOInputs inputs) {
    inputs.ClimberTorqueCurrentAmps = climbing_motor.getTorqueCurrent().getValueAsDouble();
    inputs.ClimberVelocityRotPerSec = climbing_motor.getVelocity().getValueAsDouble();
    inputs.ClimberMotorConnected = climbing_motor.isConnected();
    inputs.ClimberControlMode = climbing_motor.getControlMode().getValue();
    inputs.ClimberPositionError = climbing_motor.getClosedLoopError().getValueAsDouble();
    inputs.TilterTorqueCurrentAmps = tilting_motor.getTorqueCurrent().getValueAsDouble();
    inputs.TilterVelocityRotPerSec = tilting_motor.getVelocity().getValueAsDouble();
    inputs.TilterMotorConnected = tilting_motor.isConnected();
    inputs.TilterControlMode = tilting_motor.getControlMode().getValue();
    inputs.TilterPositionError = tilting_motor.getClosedLoopError().getValueAsDouble();
}

public void set_climb_percent_out(double percent) {
  climbing_motor.setControl(request.withOutput(percent));
}

  public void set_climbing_state(Rotation2d rot) {
    climbing_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }

  public void set_tilt_state(Rotation2d rot) {
    tilting_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }
}
