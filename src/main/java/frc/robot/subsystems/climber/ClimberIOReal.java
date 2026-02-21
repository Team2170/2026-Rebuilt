package frc.robot.subsystems.climber;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.Constants;

public class ClimberIOReal implements ClimberIO {
  private TalonFX climbing_motor;

  private DutyCycleOut request;
  private PositionDutyCycle holdPosRequest;

  public ClimberIOReal() {
    climbing_motor = new TalonFX(Constants.ClimberConstants.CLIMB_MOTOR_ID);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
    holdPosRequest = new PositionDutyCycle(0);
  }

  public void configMotor() {
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();

    internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    internalConfig.Feedback.withSensorToMechanismRatio(Constants.ClimberConstants.GEAR_RATIO);
    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    // Apply all settings.
    climbing_motor.getConfigurator().apply(internalConfig);
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
}

public void set_climb_percent_out(double percent) {
  climbing_motor.setControl(request.withOutput(percent));
  climbing_motor.setPosition(0);
}

  public void set_climbing_state(Rotation2d rot) {
    climbing_motor.setPosition(0); // MAY NOT WORK. ALTERNATIVE ALSO PRESENT.
    holdPosRequest.withFeedForward(Constants.ClimberConstants.CLIMBING_UP_SPEED);
    holdPosRequest.withPosition(climbing_motor.getRotorPosition().getValueAsDouble() + rot.getRotations()); // MAY NOT BE NECESSARY.
    climbing_motor.setControl(holdPosRequest);
  }
}