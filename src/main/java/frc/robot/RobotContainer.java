package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.climber.ClimberIO;
import frc.robot.subsystems.climber.ClimberIOReal;
import frc.robot.subsystems.climber.ClimberIOSim;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.util.OverrideSwitches;

public class RobotContainer {
    // private final Intake intake;
    // private final Shooter shooter;
    private final Climber climber;
    // private final Hopper hopper;

    private final Drive drive;
    // private final Vision limelightExample;

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);
    private final OverrideSwitches overrides = new OverrideSwitches(2); // TODO Determine port

    private final Alert driverDisconnected = new Alert("Driver controller disconnected.", AlertType.kWarning);
    private final Alert operatorDisconnected = new Alert("Operator controller disconnected.", AlertType.kWarning);
    private final Alert overrideDisconnected = new Alert("Override controller disconnected.", AlertType.kInfo);

    private final LoggedDashboardChooser<Command> autoChooser;

    public RobotContainer() {
        switch (Constants.robotMode) {
            case REAL:
                drive = new Drive(new GyroIOPigeon2(),
                        new ModuleIOTalonFX(DriveConstants.moduleConfigs[0]),
                        new ModuleIOTalonFX(DriveConstants.moduleConfigs[1]),
                        new ModuleIOTalonFX(DriveConstants.moduleConfigs[2]),
                        new ModuleIOTalonFX(DriveConstants.moduleConfigs[3]));
                climber = new Climber(new ClimberIOReal());
                break;
            default:
                drive = new Drive(
                        new GyroIO() {
                        },
                        new ModuleIOSim(),
                        new ModuleIOSim(),
                        new ModuleIOSim(),
                        new ModuleIOSim());
                climber = new Climber(new ClimberIOSim());
                break;
        }

        NamedCommands.registerCommand("climb_l1", climber.climb_to(Constants.ClimberConstants.TOWER_L1));
        // TODO: add shooter commands
        // TODO: add intake commands?

        // autoChooser = new LoggedDashboardChooser<>("Auto Routines", AutoBuilder.buildAutoChooser());
        autoChooser = new LoggedDashboardChooser<>("Auto Routines");
        LoggedDashboardChooser<Boolean> mirror = new LoggedDashboardChooser<>("blue");
        mirror.addDefaultOption(null, null);

        

        configureButtonBindings();
    }

    public void configureButtonBindings() {
        driver.leftBumper().onTrue(new InstantCommand(() -> {climber.change_climb_state(Constants.ClimberConstants.CLIMB_DOWN);})); // x on keyboard -> b on controller
        driver.rightBumper().onTrue(new InstantCommand(() -> {climber.change_climb_state(Constants.ClimberConstants.CLIMB_UP);}));
    }

    public Command getAutonomousCommand() {
        return autoChooser.get();
    }

    // Update dashboard data
    public void updateDashboardOutputs() {
        SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
    }

    public void updateAlerts() {
        // Controller disconnected alerts
        driverDisconnected.set(!DriverStation.isJoystickConnected(driver.getHID().getPort()));
        operatorDisconnected.set(!DriverStation.isJoystickConnected(operator.getHID().getPort()));
        overrideDisconnected.set(!overrides.isConnected());
    }
}