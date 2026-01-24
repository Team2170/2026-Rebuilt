package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems.Climber.Climber;
import frc.robot.Subsystems.Climber.ClimberIO;
import frc.robot.Subsystems.Drive.Drive;
import frc.robot.Subsystems.Hopper.Hopper;
import frc.robot.Subsystems.Intake.Intake;
import frc.robot.Subsystems.Shooter.Shooter;
import frc.robot.Subsystems.Vision.Vision;

public class RobotContainer {
        // private final Intake intake;
        private final Shooter shooter;
        private final Climber climber;
        // private final Hopper hopper;

        // private final Drive drive;
        // private final Vision limelightExample;

        private final CommandXboxController driverController = new CommandXboxController(0);
        // private final CommandXboxController operatorController = new
        // CommandXboxController(1);

        // private final Alert primaryDisconnected = new Alert("Primary controller
        // disconnected (port 0).",
        // AlertType.kWarning);
        // private final Alert secondaryDisconnected = new Alert("Secondary controller
        // disconnected (port 1).",
        // AlertType.kWarning);
        // private final Alert overrideDisconnected = new Alert("Override controller
        // disconnected (port 5).", AlertType.kInfo);

        // private final LoggedDashboardChooser<Command> autoChooser;

        public RobotContainer() {
climber = new Climber("name", new ClimberIO() {
    });
    configureButtonBindings();
        }

    private void configureButtonBindings() {
        driverController.a().onTrue(new InstantCommand(() -> {climber.changeState();}));
        driverController.x().onTrue(new InstantCommand(() -> {shooter.changeState();}));
    }
}