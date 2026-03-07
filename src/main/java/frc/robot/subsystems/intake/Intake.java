package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.components.IntakeIO;
import frc.robot.subsystems.intake.components.IntakeIOInputsAutoLogged;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
  private final String SubystemName;

  /**
   * Constructs a Climber subsystem.
   *
   * @param name The name of the subsystem for logging purposes.
   * @param io The ClimberIO instance handling hardware interactions.
   */
  public Intake(String name, IntakeIO io) {
    this.SubystemName = name;
    this.io = io;
  }

  /**
   * Periodic method called once per scheduler run. Updates sensor inputs and maintains position
   * when necessary.
   */
  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs(SubystemName, inputs);
  }

  /**
   * Sets the intake motor power as a percentage.
   *
   * @param percent The desired power percentage (between -1.0 and 1.0).
   */
  public void setIntakePower(double percent) {
    io.setIntakePower(percent);
  }

  /**
   * Sets the lift motor power as a percentage.
   *
   * @param percent The desired power percentage (between -1.0 and 1.0).
   */
  public void setLiftPower(double percent) {
    io.setLiftPower(percent);
  }

  /**
   * Stops the intake motor.
   */
  public void stopIntake() {
    io.stopIntake();
  }

  /**
   * Stops the lift motor.
   */
  public void stopLift() {
    io.stopLift();
  }
}