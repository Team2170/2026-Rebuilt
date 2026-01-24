package frc.robot.Subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    ShooterModule module;

    public Shooter() {

    }
    public void changeState() {
        if (on) {
            io.setPercentOut(0); on = false;
        }
        else {
            io.setPercentOut(.5); on = true;
        }
  }
}
