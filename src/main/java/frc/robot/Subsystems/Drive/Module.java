package frc.robot.Subsystems.Drive;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Alert;
import frc.robot.Subsystems.Drive.ModuleIO.ModuleIOOutputMode;
import frc.robot.Subsystems.Drive.ModuleIO.ModuleIOOutputs;

public class Module {
    private final ModuleIO io;
    private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final ModuleIOOutputs outputs = new ModuleIOOutputs();
    private final int index;

    // private SimpleMotorFeedforward ffModel = new
    // SimpleMotorFeedforward(TunerConstants.driveKs, TunerConstants.driveKv);

    private final Alert driveDisconnectedAlert;
    private final Alert turnDisconnectedAlert;

    public Module(ModuleIO io, int index) {
        this.io = io;
        this.index = index;
        driveDisconnectedAlert = new Alert("Disconnected drive motor on module " + index, Alert.AlertType.kError);
        turnDisconnectedAlert = new Alert("Disconnected turn motor on module " + index, Alert.AlertType.kError);
    }

    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drive/Module" + index, inputs);

        // Add alerts for motor disconnections

        // Maybe add log for cycle times?
    }

    public void periodicAfterScheduler() {
        io.applyOutputs(outputs);
    }

    /**
     * Runs the module with the specified setpoint state. Mutates the state to
     * optimize it.
     */
    // public void runSetpoint(SwerveModuleState state) {
    //     // Optimize velocity setpoint
    //     state.optimize(getAngle());
    //     state.cosineScale(inputs.turnPositionRads);

    //     // Apply setpoints
    //     double speedRadPerSec = state.speedMetersPerSecond / TunerConstants.wheelRadius;
    //     outputs.mode = ModuleIOOutputMode.DRIVE;
    //     outputs.driveVelocityRadPerSec = speedRadPerSec;
    //     outputs.driveFeedforward = ffModel.calculate(speedRadPerSec);
    //     outputs.turnRotation = state.angle;
    //     outputs.turnNeutral = Math.abs(state.angle.minus(getAngle()).getDegrees()) < TunerConstants.turnDeadbandDegrees;
    // }

    /**
     * Runs the module with the specified output while controlling to zero degrees.
     */
    public void runCharacterization(double output) {
        outputs.mode = ModuleIOOutputMode.CHARACTERIZE;
        outputs.driveCharacterizationOutput = output;
        outputs.turnRotation = Rotation2d.kZero;
    }

    /** Disables all motor outputs in brake mode. */
    public void brake() {
        outputs.mode = ModuleIOOutputMode.BRAKE;
    }

    /** Disables all motor outputs in coast mode. */
    public void coast() {
        outputs.mode = ModuleIOOutputMode.COAST;
    }

    /** Returns the current turn angle of the module. */
    public Rotation2d getAngle() {
        return inputs.turnPositionRads;
    }

    /** Returns the current drive position of the module in meters. */
    // public double getPositionMeters() {
    //     return inputs.drivePositionRads * TunerConstants.wheelRadius;
    // }

    /** Returns the current drive velocity of the module in meters per second. */
    // public double getVelocityMetersPerSec() {
    //     return inputs.driveVelocityRadsPerSec * TunerConstants.wheelRadius;
    // }

    /** Returns the module position (turn angle and drive position). */
    // public SwerveModulePosition getPosition() {
    //     return new SwerveModulePosition(getPositionMeters(), getAngle());
    // }

    // /** Returns the module state (turn angle and drive velocity). */
    // public SwerveModuleState getState() {
    //     return new SwerveModuleState(getVelocityMetersPerSec(), getAngle());
    // }

    /** Returns the module position in radians. */
    public double getWheelRadiusCharacterizationPosition() {
        return inputs.drivePositionRads;
    }

    /** Returns the module velocity in rotations/sec (Phoenix native units). */
    public double getFFCharacterizationVelocity() {
        return Units.radiansToRotations(inputs.driveVelocityRadsPerSec);
    }
}
