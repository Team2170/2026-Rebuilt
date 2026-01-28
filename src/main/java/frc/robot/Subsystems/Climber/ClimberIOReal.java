package frc.robot.Subsystems.Climber;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Constants.Constants;

/** Implementation of ClimberIO for real hardware, using a TalonFX motor controller. */
public class ClimberIOReal implements ClimberIO {
  private TalonFX climbing_motor;
  private TalonFX tilting_motor;
  private boolean climbing;
  /*
  * true means the climbers are tilted forward. false is the climbing position (closer to straight up)
  */
  private boolean tilt_state;
  private final static double CLIMBING_SPEED_PERCENTAGE = 0.5;
  private final static double TILTING_SPEED_PERCENTAGE = 0.3;
  private final static Rotation2d CLIMBING_TILT_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d INITIAL_TILT_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d INITIAL_HOOK_STATE = new Rotation2d(0); // TODO: FIGURE OUT
  private final static Rotation2d L1_HOOK_STATE = new Rotation2d(0);
  private final static Rotation2d L2_HOOK_STATE = new Rotation2d(0);
  private final static Rotation2d L3_HOOK_STATE = new Rotation2d(0);
  
  public static final double GEAR_RATIO = 125.00; // SET THIS UP
  private DutyCycleOut request;
  private PositionDutyCycle holdPosRequest;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration parameters.
   */
  public ClimberIOReal() {
    climbing_motor = new TalonFX(Constants.ClimberConstants.climberMotorId);
    tilting_motor = new TalonFX(Constants.ClimberConstants.tiltingMotorId);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    climbing = false;
    tilt_state = true;
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
    // TODO: figure out
    inputs.TorqueCurrentAmps = mMotor.getTorqueCurrent().getValueAsDouble();
    inputs.VelocityRotPerSec = mMotor.getVelocity().getValueAsDouble();
    inputs.MotorConnected = mMotor.isConnected();
    inputs.ControlMode = mMotor.getControlMode().getValue();
    inputs.PositionError = mMotor.getClosedLoopError().getValueAsDouble();
  }

  public void set_climbing_state() {
    climbing_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }

  public void set_tilt_state() {
    tilting_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }
}
