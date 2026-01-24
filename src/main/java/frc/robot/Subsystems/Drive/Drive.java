// Copyright (c) 2025-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.Subsystems.Drive;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
    private static final Lock odometryLock = new ReentrantLock();
    private final GyroIO gyroIO;
    private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
    private final Module[] modules = new Module[4]; // FL, FR, BL, BR
    private final Alert gyroDisconnectedAlert = new Alert("Disconnected gyro, using kinematics as fallback.",
            AlertType.kError);

    // Might be helpful
    // private static final LoggedTunableNumber coastWaitTime = new
    // LoggedTunableNumber("Drive/CoastWaitTimeSeconds", 0.5);
    // private static final LoggedTunableNumber coastMetersPerSecondThreshold = new
    // LoggedTunableNumber("Drive/CoastMetersPerSecThreshold", .05);

    private final Timer lastMovementTimer = new Timer();
    private boolean hasStartedCoast = false;

    // private SwerveDriveKinematics kinematics = new
    // SwerveDriveKinematics(TunerConstants.moduleTranslations);

    public Drive(GyroIO gyroIO, ModuleIO FLModuleIO, ModuleIO FRModuleIO, ModuleIO BLModuleIO, ModuleIO BRModuleIO) {
        this.gyroIO = gyroIO;

        modules[0] = new Module(FLModuleIO, 0);
        modules[1] = new Module(FRModuleIO, 1);
        modules[2] = new Module(BLModuleIO, 2);
        modules[3] = new Module(BRModuleIO, 3);

        lastMovementTimer.start();

        for (var module : modules) {
            module.brake();
        }
    }

    @Override
    public void periodic() {
        odometryLock.lock(); // Prevents odometry updates while reading data

        gyroIO.updateInputs(gyroInputs);
        // Log gyro inputs
        Logger.processInputs("Drive/Gyro", gyroInputs);

        for (var module : modules) {
            module.periodic();
        }

        odometryLock.unlock();

        // Log empty setpoint states when disabled
        if (DriverStation.isDisabled()) {
            Logger.recordOutput("SwerveStates/Setpoints", new SwerveModuleState[] {});
            Logger.recordOutput("SwerveStates/SetpointsOptimized", new SwerveModuleState[] {});
        }

        // TODO Update odometry

        // Add gyro disconnected alert

        // Update brake mode
        // if (Arrays.stream(modules)
        //         .anyMatch(
        //                 (module) -> Math.abs(module.getVelocityMetersPerSec()) > coastMetersPerSecondThreshold.get())) {
        //     lastMovementTimer.reset();
        // }

        // if (DriverStation.isDisabled()) {
        //     if (hasStartedCoast || lastMovementTimer.hasElapsed(coastWaitTime.get())) {
        //         for (var module : modules) {
        //             module.coast();
        //         }
        //         hasStartedCoast = true;
        //     } else {
        //         for (var module : modules) {
        //             module.brake();
        //         }
        //     }
        // } else {
        //     hasStartedCoast = false;
        // }

        // Maybe record cycle times
    }

    // TODO Add afterscheduler?

    /**
     * Runs the drive at the desired velocity.
     *
     * @param speeds Speeds in meters/sec
     */
    // public void runVelocity(ChassisSpeeds speeds) {
    // // Calculate module setpoints
    // ChassisSpeeds discreteSpeeds = ChassisSpeeds.discretize(speeds,
    // Constants.loopPeriodSecs);
    // SwerveModuleState[] setpointStates =
    // kinematics.toSwerveModuleStates(discreteSpeeds);
    // SwerveDriveKinematics.desaturateWheelSpeeds(setpointStates,
    // TunerConstants.maxLinearSpeed);

    // // Log unoptimized setpoints and setpoint speeds
    // Logger.recordOutput("SwerveStates/Setpoints", setpointStates);
    // Logger.recordOutput("SwerveChassisSpeeds/Setpoints", discreteSpeeds);

    // // Send setpoints to modules
    // for (int i = 0; i < 4; i++) {
    // modules[i].runSetpoint(setpointStates[i]);
    // }

    // // Log optimized setpoints (runSetpoint mutates each state)
    // Logger.recordOutput("SwerveStates/SetpointsOptimized", setpointStates);
    // }

    /** Stops the drive. */
    // public void stop() {
    // runVelocity(new ChassisSpeeds());
    // }

    /**
     * Stops the drive and turns the modules to an X arrangement to resist movement.
     * The modules will
     * return to their normal orientations the next time a nonzero velocity is
     * requested.
     */
    // public void stopWithX() {
    // Rotation2d[] headings = new Rotation2d[4];
    // for (int i = 0; i < 4; i++) {
    // headings[i] = TunerConstants.moduleTranslations[i].getAngle();
    // }
    // kinematics.resetHeadings(headings);
    // stop();
    // }

    /**
     * Returns the module states (turn angles and drive velocities) for all of the
     * modules.
     */
    // @AutoLogOutput(key = "SwerveStates/Measured")
    // private SwerveModuleState[] getModuleStates() {
    //     SwerveModuleState[] states = new SwerveModuleState[4];
    //     for (int i = 0; i < 4; i++) {
    //         states[i] = modules[i].getState();
    //     }
    //     return states;
    // }

    /**
     * Returns the module positions (turn angles and drive positions) for all of the
     * modules.
     */
    // private SwerveModulePosition[] getModulePositions() {
    //     SwerveModulePosition[] states = new SwerveModulePosition[4];
    //     for (int i = 0; i < 4; i++) {
    //         states[i] = modules[i].getPosition();
    //     }
    //     return states;
    // }

    /** Returns the measured chassis speeds of the robot. */
    // @AutoLogOutput(key = "SwerveChassisSpeeds/Measured")
    // private ChassisSpeeds getChassisSpeeds() {
    //     return kinematics.toChassisSpeeds(getModuleStates());
    // }

    /** Returns the position of each module in radians. */
    // public double[] getWheelRadiusCharacterizationPositions() {
    //     double[] values = new double[4];
    //     for (int i = 0; i < 4; i++) {
    //         values[i] = modules[i].getWheelRadiusCharacterizationPosition();
    //     }
    //     return values;
    // }
}
