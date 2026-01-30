package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    public static final Mode robotMode = RobotBase.isReal() ? Mode.REAL : Mode.SIM;
    public static final boolean tuningMode = false;

    public enum Mode {
        REAL,
        SIM
    }

    public static boolean disableHAL = false;

    public static void disableHAL() {
        disableHAL = true;
    }
}
