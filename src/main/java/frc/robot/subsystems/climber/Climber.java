package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.constants.Constants;
import com.ctre.phoenix6.controls.Follower;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  private TalonFX ClimberMotorLeader;
  private TalonFX ClimberMotorFollower;
  private DutyCycleOut request;

  public Climber() {
    ClimberMotorLeader = new TalonFX(Constants.ClimberConstants.ClimberMotorLeaderId);
    ClimberMotorFollower = new TalonFX(Constants.ClimberConstants.ClimberMotorFollowerId);
    configMotor();
    request = new DutyCycleOut(0).withEnableFOC(true);
  }

  public void configMotor() {
    TalonFXConfiguration internalConfig = new TalonFXConfiguration();
    ClimberMotorLeader.getConfigurator().apply(internalConfig);
    ClimberMotorFollower.getConfigurator().apply(internalConfig);

    internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    internalConfig.CurrentLimits.withStatorCurrentLimit(120);
    internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    ClimberMotorLeader.getConfigurator().apply(internalConfig);

    internalConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    ClimberMotorFollower.getConfigurator().apply(internalConfig);

  }

  public void liftUp(double percent) {
    ClimberMotorLeader.setControl(request.withOutput(percent));
  }

  public void liftDown(double percent) {
    ClimberMotorLeader.setControl(request.withOutput(-percent));
  }


  public void stop() {
    ClimberMotorLeader.stopMotor();
    ClimberMotorFollower.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}