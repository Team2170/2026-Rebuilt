package frc.robot.Subsystems.Climber;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.Constants;

/** Implementation of ClimberIO for real hardware, using a TalonFX motor controller. */
public class ClimberIOReal implements ClimberIO {
  private TalonFX climbing_motor_1;
  private TalonFX climbing_motor_2;
  private TalonFX tilting_motor_1;
  private TalonFX tilting_motor_2;
  public static final double GEAR_RATIO = 12.00; // SET THIS UP
  private DutyCycleOut request;
  private PositionDutyCycle holdPosRequest;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration parameters.
   */
  public ClimberIOReal() {
    climbing_motor_1 = new TalonFX(Constants.ClimberConstants.climberMotor1Id);
    climbing_motor_2 = new TalonFX(Constants.ClimberConstants.climberMotor2Id);
    tilting_motor_1 = new TalonFX(Constants.ClimberConstants.tiltingMotor1Id);
    tilting_motor_2 = new TalonFX(Constants.ClimberConstants.tiltingMotor2Id);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    holdPosRequest= new PositionDutyCycle(0);
    holdPosRequest.Velocity = 0.3; //TODO: change later 
    
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
    climbing_motor_1.getConfigurator().apply(internalConfig);
    climbing_motor_2.getConfigurator().apply(internalConfig);
    tilting_motor_1.getConfigurator().apply(internalConfig);
    tilting_motor_2.getConfigurator().apply(internalConfig);
  }

  /**
   * Updates the input state with the current sensor values.
   *
   * @param inputs The ClimberIOInputs object to update.
   */
 // public void updateInputs(ClimberIOInputs inputs) {
    /*inputs.TorqueCurrentAmps = mMotor.getTorqueCurrent().getValueAsDouble();
    inputs.VelocityRotPerSec = mMotor.getVelocity().getValueAsDouble();
    inputs.MotorConnected = mMotor.isConnected();
    inputs.ControlMode = mMotor.getControlMode().getValue();
    inputs.PositionError = mMotor.getClosedLoopError().getValueAsDouble();*/ // TODO: decide what to do with this
//}

  /**
   * Sets the motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public void setPercentOut(double percent) {
    climbing_motor_1.setControl(request.withOutput(percent));
    climbing_motor_2.setControl(request.withOutput(percent));
  }

  /**
   * Holds the climber at a specific position.
   *
   * @param rot The target position in rotations.
   */
  public void hold_tilt(Rotation2d rot) {
    tilting_motor_1.setControl(holdPosRequest.withPosition(rot.getRotations()));
    tilting_motor_2.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }

  public void hold_hooks(Rotation2d rot) {
    climbing_motor_1.setControl(holdPosRequest.withPosition(rot.getRotations()));
    climbing_motor_2.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }
}