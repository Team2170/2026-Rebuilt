// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.constants;

import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;

import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import frc.robot.generated.TunerConstants;
import frc.robot.util.LoggedTunableNumber;
import frc.robot.util.swerve.ModuleLimits;
import lombok.Builder;

public class DriveConstants {
        public static final double odometryFrequency = 250;

        public static final double trackWidthX = Units.inchesToMeters(22.5);
        public static final double trackWidthY = Units.inchesToMeters(22.5);
        public static final double wheelRadius = Units.inchesToMeters(2);
        public static final double driveBaseRadius = Math.hypot(trackWidthX / 2, trackWidthY / 2);

        public static final LoggedTunableNumber maxLinearSpeed = new LoggedTunableNumber("Drive Max Linear Speed",
                        4.69);
        public static final LoggedTunableNumber maxAngularSpeed = new LoggedTunableNumber("Drive Max Angular Speed",
                        4.69 / driveBaseRadius);
        public static final LoggedTunableNumber maxLinearAcceleration = new LoggedTunableNumber(
                        "Drive Max Linear Acceleration", 22.0);
        public static final LoggedTunableNumber turnDeadbandDegrees = new LoggedTunableNumber(
                        "Drive Turn Deadband Degrees", 0.3);

        // TODO In meters
        public static final Translation2d[] moduleTranslations = {
                        new Translation2d(trackWidthX / 2, trackWidthY / 2),
                        new Translation2d(trackWidthX / 2, -trackWidthY / 2),
                        new Translation2d(-trackWidthX / 2, trackWidthY / 2),
                        new Translation2d(-trackWidthX / 2, -trackWidthY / 2)
        };

        // PathPlanner config constants
        private static final double ROBOT_MASS_KG = 74.088;
        private static final double ROBOT_MOI = 6.883;
        private static final double WHEEL_COF = 1.2;
        private static final RobotConfig PP_CONFIG = new RobotConfig(
                        ROBOT_MASS_KG,
                        ROBOT_MOI,
                        new ModuleConfig(
                                        TunerConstants.FrontLeft.WheelRadius,
                                        TunerConstants.kSpeedAt12Volts.in(MetersPerSecond),
                                        WHEEL_COF,
                                        DCMotor.getKrakenX60Foc(1)
                                                        .withReduction(TunerConstants.FrontLeft.DriveMotorGearRatio),
                                        TunerConstants.FrontLeft.SlipCurrent,
                                        1),
                        moduleTranslations);

        public static final ModuleLimits moduleLimitsFree = new ModuleLimits(maxLinearSpeed.get(),
                        maxLinearAcceleration.get(), Units.degreesToRadians(1080.0));

        // TODO Fill in actual module config values
        public static final ModuleConfigs[] moduleConfigs = {
                        // FL
                        ModuleConfigs.builder()
                                        .driveMotorId(16)
                                        .turnMotorId(15)
                                        .encoderChannel(41)
                                        .encoderOffset(Rotation2d.fromRadians(2.5356702423749646))
                                        .turnInverted(true)
                                        .encoderInverted(false)
                                        .build(),
                        // FR
                        ModuleConfigs.builder()
                                        .driveMotorId(10)
                                        .turnMotorId(11)
                                        .encoderChannel(42)
                                        .encoderOffset(Rotation2d.fromRadians(-2.932971266437346))
                                        .turnInverted(true)
                                        .encoderInverted(false)
                                        .build(),
                        // BL
                        ModuleConfigs.builder()
                                        .driveMotorId(18)
                                        .turnMotorId(19)
                                        .encoderChannel(43)
                                        .encoderOffset(Rotation2d.fromRadians(0.6458059116998549))
                                        .turnInverted(true)
                                        .encoderInverted(false)
                                        .build(),
                        // BR
                        ModuleConfigs.builder()
                                        .driveMotorId(13)
                                        .turnMotorId(14)
                                        .encoderChannel(44)
                                        .encoderOffset(Rotation2d.fromRadians(-2.5187964537082226))
                                        .turnInverted(true)
                                        .encoderInverted(false)
                                        .build()
        };

        public static final DriveTrainSimulationConfig mapleSimConfig = DriveTrainSimulationConfig.Default()
                        .withRobotMass(Kilograms.of(ROBOT_MASS_KG))
                        .withCustomModuleTranslations(moduleTranslations)
                        .withGyro(COTS.ofPigeon2())
                        .withSwerveModule(new SwerveModuleSimulationConfig(
                                        DCMotor.getKrakenX60(1),
                                        DCMotor.getKrakenX60(1),
                                        TunerConstants.FrontLeft.DriveMotorGearRatio,
                                        TunerConstants.FrontLeft.SteerMotorGearRatio,
                                        Volts.of(TunerConstants.FrontLeft.DriveFrictionVoltage),
                                        Volts.of(TunerConstants.FrontLeft.SteerFrictionVoltage),
                                        Meters.of(TunerConstants.FrontLeft.WheelRadius),
                                        KilogramSquareMeters.of(TunerConstants.FrontLeft.SteerInertia),
                                        WHEEL_COF));

        // TODO Fill in actual pigeon ID
        public static class PigeonConstants {
                public static final int id = 0;
        }

        @Builder
        public record ModuleConfigs(
                        int driveMotorId,
                        int turnMotorId,
                        int encoderChannel,
                        Rotation2d encoderOffset,
                        boolean turnInverted,
                        boolean encoderInverted) {
        }
}