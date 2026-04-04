// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import java.io.IOException;
import java.util.Optional;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.json.simple.parser.ParseException;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.DriveCommands;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ShooterConstants;
import frc.robot.constants.Constants.VisionConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.GyroIOSim;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.hopper.components.HopperIOReal;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.components.ShooterIOTalonFX;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.components.VisionIO;
import frc.robot.subsystems.vision.components.VisionIOLimelight;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic
 * methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and
 * button mappings) should be declared here.
 */
public class RobotContainer {
	private final Vision vision;
	private final Drive drive;
	private final Hopper hopper;
	private final Shooter shooter;
	private SwerveDriveSimulation driveSimulation = null;

	public final CommandXboxController driverController = new CommandXboxController(0);

	private final CommandXboxController operatorController = new CommandXboxController(1);

	// Dashboard inputs
	private final LoggedDashboardChooser<Command> autoChooser;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		switch (Constants.currentMode) {
			case REAL:
				// Real robot, instantiate hardware IO implementations
				drive = new Drive(
						new GyroIOPigeon2(),
						new ModuleIOTalonFX(TunerConstants.FrontLeft),
						new ModuleIOTalonFX(TunerConstants.FrontRight),
						new ModuleIOTalonFX(TunerConstants.BackLeft),
						new ModuleIOTalonFX(TunerConstants.BackRight),
						(robotPose) -> {
						});
				vision = new Vision(
						drive,
						new VisionIOLimelight(VisionConstants.rightCameraName, drive::getRotation));

				shooter = new Shooter("Shooter", new ShooterIOTalonFX());
				hopper = new Hopper("Hopper", new HopperIOReal());
				break;

			case SIM:
				// Sim robot, instantiate physics sim IO implementations
				driveSimulation = new SwerveDriveSimulation(Drive.getMapleSimConfig(),
						new Pose2d(3, 3, new Rotation2d()));
				SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
				drive = new Drive(
						new GyroIOSim(driveSimulation.getGyroSimulation()),
						new ModuleIOSim(driveSimulation.getModules()[0]),
						new ModuleIOSim(driveSimulation.getModules()[1]),
						new ModuleIOSim(driveSimulation.getModules()[2]),
						new ModuleIOSim(driveSimulation.getModules()[3]),
						driveSimulation::setSimulationWorldPose);

				vision = null; // Vision is not supported in simulation yet
				hopper = null;
				shooter = null;
				break;

			default:
				// Replayed robot, disable IO implementations
				drive = new Drive(
						new GyroIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						},
						(robotPose) -> {
						});
				vision = new Vision(drive, new VisionIO() {
				}, new VisionIO() {
				});
				hopper = null;
				shooter = null;
				break;
		}

		// Set up auto routines
		autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

		// Set up SysId routines
		autoChooser.addOption("Drive Wheel Radius Characterization",
				DriveCommands.wheelRadiusCharacterization(drive));
		autoChooser.addOption("Drive Simple FF Characterization",
				DriveCommands.feedforwardCharacterization(drive));
		autoChooser.addOption(
				"Drive SysId (Quasistatic Forward)",
				drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
		autoChooser.addOption(
				"Drive SysId (Quasistatic Reverse)",
				drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
		autoChooser.addOption("Drive SysId (Dynamic Forward)",
				drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
		autoChooser.addOption("Drive SysId (Dynamic Reverse)",
				drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
		autoChooser.addOption("Shoot To Hub", new ParallelCommandGroup(
				new InstantCommand(
						() -> shooter.setShooterVelocityOut(
								vision.hasAnyTarget()
										? shooter.calculateRPS(
												vision.getTagDistance(vision.getBestCameraIndex()),
												vision.getTargetY(vision.getBestCameraIndex()).getDegrees())
										: 50),
						shooter),
				new WaitCommand(1).andThen(new InstantCommand(
						() -> shooter.setFeedMotorVelocityOut(ShooterConstants.FeedMotorRPS))))
				.andThen(new WaitCommand(5)).andThen(new InstantCommand(() -> shooter.stop())));

		Command shootToHub = new ParallelCommandGroup(
				new InstantCommand(
						() -> shooter.setShooterVelocityOut(
								vision.hasAnyTarget()
										? shooter.calculateRPS(
												vision.getTagDistance(vision.getBestCameraIndex()),
												vision.getTargetY(vision.getBestCameraIndex()).getDegrees())
										: 50),
						shooter),
				new WaitCommand(1).andThen(new InstantCommand(
						() -> shooter.setFeedMotorVelocityOut(ShooterConstants.FeedMotorRPS))))
				.andThen(new WaitCommand(5)).andThen(new InstantCommand(() -> shooter.stop()));

		try {
			autoChooser.addOption("Potential Shoot?",
					AutoBuilder.followPath(PathPlannerPath.fromPathFile("Running Rotation"))
							.deadlineFor(new ParallelCommandGroup(
									new InstantCommand(() -> shooter.setShooterVelocityOut(5)),
									new WaitCommand(0.5).andThen(new InstantCommand(
											() -> shooter.setFeedMotorVelocityOut(ShooterConstants.FeedMotorRPS))))));
			autoChooser.addOption("To Middle", AutoBuilder.followPath(PathPlannerPath.fromPathFile("To Middle")));
			autoChooser.addOption("RAHHHHHH", AutoBuilder.followPath(PathPlannerPath.fromPathFile("RAHHHHHH")));
			autoChooser.addOption("A",
					AutoBuilder.followPath(PathPlannerPath.fromPathFile("I Guess Bro")).andThen(shootToHub));
		} catch (FileVersionException | IOException | ParseException e) {
			DriverStation.reportError("Failed to load auto paths", e.getStackTrace());
			e.printStackTrace();
		}

		// TODO: if this doesn't work, add an event marker in pathplanner for shooting
		try {

			autoChooser.addOption("top to center and back",
					AutoBuilder.followPath(PathPlannerPath.fromPathFile("top to center and back")).andThen(shootToHub));
			autoChooser.addOption("middle to center and back", AutoBuilder
					.followPath(PathPlannerPath.fromPathFile("middle to center and back")).andThen(shootToHub));
			autoChooser.addOption("bottom to center and back", AutoBuilder
					.followPath(PathPlannerPath.fromPathFile("bottom to center and back")).andThen(shootToHub));
		} catch (FileVersionException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: add intake commands here too
		NamedCommands.registerCommand("Start Intake", new InstantCommand(() -> {
			hopper.extendHopper();
		}));
		NamedCommands.registerCommand("End Intake", new InstantCommand(() -> {
			hopper.retractHopper();
		}));

		// Configure the button bindings
		configureButtonBindings();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a
	 * {@link GenericHID} or one of its subclasses
	 * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
	 * and then passing it to a
	 * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Default command, normal field-relative drive
		drive.setDefaultCommand(DriveCommands.joystickDrive(
				drive, () -> -driverController.getLeftY(),
				() -> -driverController.getLeftX(),
				() -> driverController.getRightX()));

		// Lock to 0° when A button is held
		driverController
				.a()
				.whileTrue(DriveCommands.joystickDriveAtAngle(
						drive, () -> -driverController.getLeftY(), () -> -driverController.getLeftX(),
						() -> new Rotation2d()));

		driverController.rightBumper().whileTrue(DriveCommands.joystickAngleToTag(
				drive,
				vision,
				() -> -driverController.getLeftY(),
				() -> -driverController.getLeftX()));

		// Switch to X pattern when X button is pressed
		driverController.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

		// Reset gyro / odometry
		final Runnable resetOdometry = Constants.currentMode == Constants.Mode.SIM
				? () -> drive.resetOdometry(driveSimulation.getSimulatedDriveTrainPose())
				: () -> drive.resetOdometry(
						new Pose2d(drive.getPose().getTranslation(), new Rotation2d()));
		driverController.a().onTrue(Commands.runOnce(resetOdometry).ignoringDisable(true));

		// operatorController.a().onTrue(
		// new ParallelCommandGroup(
		// new InstantCommand(
		// () -> shooter.setShooterVelocityOut(
		// vision.hasAnyTarget()
		// ? shooter.calculateRPS(
		// vision.getTagDistance(vision.getBestCameraIndex()),
		// vision.getTargetY(vision.getBestCameraIndex()).getDegrees())
		// : 50),
		// shooter),
		// new WaitUntilCommand(() -> shooter.atRPS()).andThen(new InstantCommand(
		// () -> shooter.setFeedMotorVelocityOut(ShooterConstants.FeedMotorRPS)))))
		// .onFalse(new InstantCommand(() -> shooter.stop()));

		// TODO Add separate rev to speed motor

		// TODO While shooting, retract hopper

		// operatorController.y().onTrue(new ParallelCommandGroup(
		// new InstantCommand(() -> shooter.setShooterVelocityOut(50)), new
		// WaitCommand(1).andThen(
		// new InstantCommand(() ->
		// shooter.setFeedMotorVelocityOut(ShooterConstants.FeedMotorRPS)))))
		// .onFalse(new InstantCommand(() -> shooter.stop()));

		// // operatorController.rightBumper().onTrue(new InstantCommand(() ->
		// hopper.setIntakePower(-0.5)))
		// .onFalse(new InstantCommand(() -> hopper.stopIntake()));

		// // operatorController.b().onTrue(new InstantCommand(() ->
		// hopper.setIntakePower(0.65)))
		// .onFalse(new InstantCommand(() -> hopper.stopIntake()));

		// // operatorController.povUp()
		// .onTrue(new ParallelCommandGroup(new InstantCommand(() ->
		// hopper.setHopperPower(0.2)),
		// new InstantCommand(() -> hopper.setIntakePower(0.2))))
		// .onFalse(new ParallelCommandGroup(new InstantCommand(() ->
		// hopper.stopHopper()),
		// new InstantCommand(() -> hopper.stopIntake())));

		// operatorController.povDown().onTrue(new InstantCommand(() ->
		// hopper.setHopperPower(-0.2)))
		// .onFalse(new InstantCommand(() -> hopper.stopHopper()));
	}

	public void periodic() {
		SmartDashboard.putBoolean("Auto Aligned", DriveCommands.autoAligned);
		SmartDashboard.putBoolean("Hub Active", isHubActive());
		SmartDashboard.putNumber("Time Until Shift", timeUntilShift());
	}

	public int timeUntilShift() {
		double matchTime = DriverStation.getMatchTime();
		if (matchTime > 130) {
			return -1; // N/A (Transition)
		} else if (matchTime > 105) {
			return (int) (matchTime - 105);
		} else if (matchTime > 80) {
			return (int) (matchTime - 80);
		} else if (matchTime > 55) {
			return (int) (matchTime - 55);
		} else if (matchTime > 30) {
			return (int) (matchTime - 30);
		} else {
			return -1; // N/A (Endgame)
		}
	}

	public boolean isHubActive() {
		Optional<Alliance> alliance = DriverStation.getAlliance();

		// No alliance = can't be enabled
		if (alliance.isEmpty())
			return false;

		// Hub is always active in Auto
		if (DriverStation.isAutonomousEnabled())
			return true;

		// Not in teleop = no hub
		if (!DriverStation.isTeleopEnabled())
			return false;

		double matchTime = DriverStation.getMatchTime();
		String gameData = DriverStation.getGameSpecificMessage();

		// No data yet (early teleop) — assume active
		if (gameData.isEmpty())
			return true;

		boolean redInactiveFirst = false;
		switch (gameData.charAt(0)) {
			case 'R' -> redInactiveFirst = true;
			case 'B' -> redInactiveFirst = false;
			default -> {
				return true;
			} // corrupt data, assume active
		}

		// Shift 1 is active for Blue if Red won auto, or Red if Blue won auto
		boolean shift1Active = switch (alliance.get()) {
			case Red -> !redInactiveFirst;
			case Blue -> redInactiveFirst;
		};

		if (matchTime > 130)
			return true; // Transition shift — always active
		else if (matchTime > 105)
			return shift1Active; // Shift 1
		else if (matchTime > 80)
			return !shift1Active; // Shift 2
		else if (matchTime > 55)
			return shift1Active; // Shift 3
		else if (matchTime > 30)
			return !shift1Active; // Shift 4
		else
			return true; // End game — always active
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return autoChooser.get();
	}

	public void resetSimulation() {
		if (Constants.currentMode != Constants.Mode.SIM)
			return;

		drive.resetOdometry(new Pose2d(1, 1, new Rotation2d()));
		SimulatedArena.getInstance().resetFieldForAuto();
	}

	public void updateSimulation() {
		if (Constants.currentMode != Constants.Mode.SIM)
			return;

		SimulatedArena.getInstance().simulationPeriodic();
		Logger.recordOutput("FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
		// Logger.recordOutput("FieldSimulation/Fuel",
		// SimulatedArena.getInstance().getGamePiecesArrayByType("Fuel"));
	}
}