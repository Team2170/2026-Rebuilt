package frc.robot.subsystems.climber;

import edu.wpi.first.math.geometry.Rotation2d;

public class ClimberIOSim implements ClimberIO {

    @Override
    public void set_tilt_state(Rotation2d rot) {
        System.out.println("Tilt state set to "  + rot.getRotations() + " rotations.");
    }

    @Override
    public void set_climbing_state(Rotation2d rot) {
        System.out.println("Climbing state set to " + rot.getRotations() + " rotations.");
    }

}
