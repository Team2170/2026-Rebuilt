// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.util.LoggedTunableNumber;
import frc.robot.util.swerve.ModuleLimits;
import lombok.Builder;

public class DriveConstants {
    public static final double odometryFrequency = 250;

    // TODO In meters
    public static final double trackWidthX = 0;
    public static final double trackWidthY = 0;
    public static final double wheelRadius = 0;
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
            new Translation2d(),
            new Translation2d(),
            new Translation2d(),
            new Translation2d()
    };

    public static final ModuleLimits moduleLimitsFree = new ModuleLimits(maxLinearSpeed.get(),
            maxLinearAcceleration.get(), Units.degreesToRadians(1080.0));

    // TODO Fill in actual module config values
    public static final ModuleConfig[] moduleConfigs = {
            // FL
            ModuleConfig.builder()
                    .driveMotorId(16)
                    .turnMotorId(15)
                    .encoderChannel(41)
                    .encoderOffset(Rotation2d.fromRadians(2.5356702423749646))
                    .turnInverted(true)
                    .encoderInverted(false)
                    .build(),
            // FR
            ModuleConfig.builder()
                    .driveMotorId(10)
                    .turnMotorId(11)
                    .encoderChannel(42)
                    .encoderOffset(Rotation2d.fromRadians(-2.932971266437346))
                    .turnInverted(true)
                    .encoderInverted(false)
                    .build(),
            // BL
            ModuleConfig.builder()
                    .driveMotorId(18)
                    .turnMotorId(19)
                    .encoderChannel(43)
                    .encoderOffset(Rotation2d.fromRadians(0.6458059116998549))
                    .turnInverted(true)
                    .encoderInverted(false)
                    .build(),
            // BR
            ModuleConfig.builder()
                    .driveMotorId(13)
                    .turnMotorId(14)
                    .encoderChannel(44)
                    .encoderOffset(Rotation2d.fromRadians(-2.5187964537082226))
                    .turnInverted(true)
                    .encoderInverted(false)
                    .build()
    };

    //TODO Fill in actual pigeon ID
    public static class PigeonConstants {
        public static final int id = 0;
    }

    @Builder
    public record ModuleConfig(
            int driveMotorId,
            int turnMotorId,
            int encoderChannel,
            Rotation2d encoderOffset,
            boolean turnInverted,
            boolean encoderInverted) {
    }
}