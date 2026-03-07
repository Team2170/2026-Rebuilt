package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.shooter.components.ShooterIO;
import frc.robot.subsystems.shooter.components.ShooterIOInputsAutoLogged;

public class Shooter extends SubsystemBase {
  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();
  private final String SubystemName;

  /**
   * Constructs a Climber subsystem.
   *
   * @param name The name of the subsystem for logging purposes.
   * @param io The ClimberIO instance handling hardware interactions.
   */
  public Shooter(String name, ShooterIO io) {
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
   * Sets the motor output as a percentage of total power.
   *
   * @param rps The desired velocity in rotations per second.
   */
  public void setVelocityOut(double rps) {
    io.setVelocityOut(rps);
  }

  // public void setPercentOut(double percent) {
  //   io.setPercentOut(percent);
  // }
  
  /** Stops the shooter, setting the output to zero and maintaining position. */
  public void stop() {
    io.stop();
  }
}
