package frc.robot.Constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    public static final Mode robotMode = RobotBase.isReal() ? Mode.REAL : Mode.SIM;

    public enum Mode {
        REAL,
        SIM,
        // REPLAY (Honestly this is a maybe)
    }
}
