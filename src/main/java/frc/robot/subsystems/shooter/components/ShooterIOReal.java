package frc.robot.subsystems.shooter.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import frc.robot.constants.Constants.ShooterConstants;
import frc.robot.constants.Constants.VisionConstants;

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

	private boolean motorsOff;

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

		motorsOff = true;
	}

	/** Configures the motor with the provided parameters. */
	public void configMotors() {
		TalonFXConfiguration internalConfig = new TalonFXConfiguration();
		BackFollowerMotor.getConfigurator().apply(internalConfig);
		BackMasterMotor.getConfigurator().apply(internalConfig);

		internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
		internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
		internalConfig.Feedback.withSensorToMechanismRatio(BACK_GEAR_RATIO);
		internalConfig.CurrentLimits.withStatorCurrentLimit(60);
		internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
		internalConfig.Slot0.kP = 0.1; // Responds to velocity error
		internalConfig.Slot0.kI = 0.001; // Integrates accumulated error (Not rlly needed)
		internalConfig.Slot0.kD = 0.02; // Dampens oscillation on sudden load
		internalConfig.Slot0.kV = 0.12; // Feedforward: volts per RPS
		internalConfig.Slot0.kS = 0.24; // Static friction offset (gets motor moving)
		internalConfig.Slot0.kA = 0.001; // Acceleration feedforward

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

	public double calculateRPS(double tagDistance, double ty) {
		double distanceToTarget = tagDistance / Math.cos(Units.degreesToRadians(VisionConstants.degreesOffset + ty));
		double rps = 0.244898 * distanceToTarget + 30;
		return rps;
	}

	/**
	 * Sets the motor output as a percentage of total power.
	 *
	 * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
	 */
	public void setShooterVelocityOut(double rps) {
		motorsOff = false;
		
		BackMasterMotor.setControl(request.withVelocity(rps));
		BackFollowerMotor.setControl(request.withVelocity(rps));
	}

	public void setFeedMotorVelocityOut(double rps) {
		if (!motorsOff) {
			FrontMotor.setControl(request.withVelocity(rps));
		}
	}

	public void stop() {
		motorsOff = true;

		BackFollowerMotor.stopMotor();
		BackMasterMotor.stopMotor();
		FrontMotor.stopMotor();
	}
}