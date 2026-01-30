// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import lombok.Builder;

public class DriveConstants {
    public static final double odometryFrequency = 250;

    // TODO In meters
    public static final Translation2d[] moduleTranslations = {
            new Translation2d(),
            new Translation2d(),
            new Translation2d(),
            new Translation2d()
    };

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