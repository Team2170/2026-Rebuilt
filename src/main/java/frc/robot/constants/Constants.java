package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    public static final double loopPeriodSecs = 0.02;
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



    public static final class ShooterConstants{


        public static final int shooterID = 0;//*** CHANGE THESE, JUST AN EXAMPLE */
        public static final int followerID = 0;//*** CHANGE THESE, JUST AN EXAMPLE */

        public static final class FieldConstants{

            private static final double hubHeight = 49.5; //inches


        }

        





    }


}
