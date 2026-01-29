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
<<<<<<< HEAD
  private TalonFX climbing_motor_1;
  private TalonFX climbing_motor_2;
  private TalonFX tilting_motor_1;
  private TalonFX tilting_motor_2;
  public static final double GEAR_RATIO = 12.00; // SET THIS UP
=======
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
>>>>>>> c817183dca39530bb01e214af50f2466a0bbe856
  private DutyCycleOut request;
  private PositionDutyCycle holdPosRequest;

  /**
   * Constructs a ClimberIOReal instance with the given configuration.
   *
   * @param cfg The ClimberConfiguration object containing configuration parameters.
   */
  public ClimberIOReal() {
<<<<<<< HEAD
    climbing_motor_1 = new TalonFX(Constants.ClimberConstants.climberMotor1Id);
    climbing_motor_2 = new TalonFX(Constants.ClimberConstants.climberMotor2Id);
    tilting_motor_1 = new TalonFX(Constants.ClimberConstants.tiltingMotor1Id);
    tilting_motor_2 = new TalonFX(Constants.ClimberConstants.tiltingMotor2Id);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    holdPosRequest= new PositionDutyCycle(0);
    holdPosRequest.Velocity = 0.3; //TODO: change later 
    
=======
    climbing_motor = new TalonFX(Constants.ClimberConstants.climberMotorId);
    tilting_motor = new TalonFX(Constants.ClimberConstants.tiltingMotorId);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    climbing = false;
    tilt_state = true;
>>>>>>> c817183dca39530bb01e214af50f2466a0bbe856
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
<<<<<<< HEAD
    climbing_motor_1.getConfigurator().apply(internalConfig);
    climbing_motor_2.getConfigurator().apply(internalConfig);
    tilting_motor_1.getConfigurator().apply(internalConfig);
    tilting_motor_2.getConfigurator().apply(internalConfig);
  }

=======
    climbing_motor.getConfigurator().apply(internalConfig);
    tilting_motor.getConfigurator().apply(internalConfig);
  }
  
>>>>>>> c817183dca39530bb01e214af50f2466a0bbe856
  /**
   * Updates the input state with the current sensor values.
   *
   * @param inputs The ClimberIOInputs object to update.
   */
<<<<<<< HEAD
 // public void updateInputs(ClimberIOInputs inputs) {
    /*inputs.TorqueCurrentAmps = mMotor.getTorqueCurrent().getValueAsDouble();
=======
  public void updateInputs(ClimberIOInputs inputs) {
    // TODO: figure out
    inputs.TorqueCurrentAmps = mMotor.getTorqueCurrent().getValueAsDouble();
>>>>>>> c817183dca39530bb01e214af50f2466a0bbe856
    inputs.VelocityRotPerSec = mMotor.getVelocity().getValueAsDouble();
    inputs.MotorConnected = mMotor.isConnected();
    inputs.ControlMode = mMotor.getControlMode().getValue();
    inputs.PositionError = mMotor.getClosedLoopError().getValueAsDouble();*/ // TODO: decide what to do with this
//}

<<<<<<< HEAD
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
  public void hold(double rot) {
    tilting_motor_1.setControl(holdPosRequest.withPosition(rot));
    tilting_motor_2.setControl(holdPosRequest.withPosition(rot));
  }
}
=======
  public void set_climbing_state() {
    climbing_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }

  public void set_tilt_state() {
    tilting_motor.setControl(holdPosRequest.withPosition(rot.getRotations()));
  }
}
>>>>>>> c817183dca39530bb01e214af50f2466a0bbe856
