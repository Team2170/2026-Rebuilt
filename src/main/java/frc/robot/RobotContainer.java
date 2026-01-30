package frc.robot;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;

public class RobotContainer {
  // private final Intake intake;
  // private final Shooter shooter;
  // private final Climber climber;
  // private final Hopper hopper;

  // private final Drive drive;
  // private final Vision limelightExample;

  private final XboxController driverController = new XboxController(0);
  private final XboxController operatorController = new XboxController(1);

  private final Alert primaryDisconnected = new Alert("Primary controller disconnected (port 0).",
      AlertType.kWarning);
  private final Alert secondaryDisconnected = new Alert("Secondary controller disconnected (port 1).",
      AlertType.kWarning);
  private final Alert overrideDisconnected = new Alert("Override controller disconnected (port 5).", AlertType.kInfo);

  private final LoggedDashboardChooser<Command> autoChooser;

  public RobotContainer() {
    switch (Constants.robotMode) {
      case REAL:
        // Not implemented
        break;

      case SIM:
        // Not implemented
        break;
    }

    autoChooser = new LoggedDashboardChooser<>("Auto Routines", AutoBuilder.buildAutoChooser());

    // TODO add sysId routines

    configureButtonBindings();
  }

  public void configureButtonBindings() {

  }

  public Command getAutonomousCommand() {
    return autoChooser.get();
  }

  public void resetSimulationField() {
    if (Constants.robotMode != Constants.Mode.SIM) {
      return;
    }

    driveSimulation.setSimulationWorldPose(new Pose2d(3, 3, new Rotation2d()));
    SimulatedArena.getInstance().resetFieldForAuto();
  }

  public void updateSimulation() {
    if (Constants.robotMode != Constants.Mode.SIM) {
      return;
    }

    SimulatedArena.getInstance().simulationPeriodic();
    
    Logger.recordOutput("FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
    Logger.recordOutput(
        "FieldSimulation/Coral", SimulatedArena.getInstance().getGamePiecesArrayByType("Coral"));
    Logger.recordOutput(
        "FieldSimulation/Algae", SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
  }
}