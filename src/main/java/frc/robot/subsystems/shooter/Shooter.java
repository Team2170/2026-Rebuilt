package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.shooter.components.ShooterIO;

public class Shooter extends SubsystemBase {
  private final ShooterIO io;
  private final String SubystemName;
  private boolean shooting;

  /**
   * Constructs a Climber subsystem.
   *
   * @param name The name of the subsystem for logging purposes.
   * @param io   The ClimberIO instance handling hardware interactions.
   */
  public Shooter(String name, ShooterIO io) {
    this.SubystemName = name;
    this.io = io;
  }

  /**
   * Periodic method called once per scheduler run. Updates sensor inputs and
   * maintains position
   * when necessary.
   */
  @Override
  public void periodic() {
  }

  /**
   * Sets the motor output as a percentage of total power.
   *
   * @param percent The percentage output to set the climber motor (-1.0 to 1.0).
   */
  public void setPercentOut(double percent) {
    io.setPercentOut(percent);
  }

  /** Stops the climber, setting the output to zero and maintaining position. */
  public void stop() {
    io.stop();
  }

  public boolean isShooting() {
    return shooting;
  }

  public void setShooting(boolean shooting) {
    this.shooting = shooting;
  }
}
