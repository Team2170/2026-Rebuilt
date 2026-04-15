package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.Vision;

//New command for AutoAlign
public class AutoAlignToHub extends Command {

    private final ProfiledPIDController rotPID = new ProfiledPIDController(
        //* THESE VALUES NEED ACTUAL TUNING!!! */
        0.1,  // kP — tune this first
        0.0,  // kI
        0.01, // kD — increase if oscillating 
        new TrapezoidProfile.Constraints(
            Math.toRadians(360), // max rotation speed (rad/s)
            Math.toRadians(540)  // max acceleration (rad/s²)
        )
    );

    private final Drive drive;
    private final Vision vision;

    public AutoAlignToHub(Drive drive, Vision vision) {
        this.drive  = drive;
        this.vision = vision;
        rotPID.setTolerance(Math.toRadians(1.5)); // done within 1.5 degrees
        addRequirements(drive); // Vision is read-only, do NOT require it!
    }

    @Override
    public void initialize() {
        rotPID.reset(0);
    }

    @Override
    public void execute() {
        //If limelight is not able to see tags, it does not move/turn the robot
        if (!vision.canSeeHubTags()) {
            drive.runVelocity(new ChassisSpeeds(0, 0, 0));
            return;
        }

        double rotOutput = rotPID.calculate(
            Math.toRadians(vision.getHubYawError()),
            0.0
        );

        /*Actually turns robot*/
        drive.runVelocity(new ChassisSpeeds(0, 0, -rotOutput));
    }

    @Override
    public void end(boolean interrupted) {
        drive.runVelocity(new ChassisSpeeds(0, 0, 0));
    }

    @Override
    public boolean isFinished() {
        // Two conditions must BOTH be true before declaring finish:
        // 1. Hub tags are still visible — confirms we aligned to a real target
        // 2. PID is at setpoint — robot is within 1.5 degrees of hub center
        // If tags disappear right as we finish, we do not declare finish
        return vision.canSeeHubTags() && rotPID.atSetpoint();
    }
}
