package frc.robot.subsystems.drive;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.littletonrobotics.junction.AutoLogOutput;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.swerve.SwerveSetpoint;
import frc.robot.util.swerve.SwerveSetpointGenerator;

public class Drive extends SubsystemBase {
        public static final Lock odometryLock = new ReentrantLock();
        private final GyroIO gyroIO;
        private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
        private final Module[] modules = new Module[4]; // FL, FR, BL, BR
        private final Debouncer gyroConnectedDebouncer = new Debouncer(0.5, Debouncer.DebounceType.kFalling);
        private final Alert gyroDisconnectedAlert = new Alert("Disconnected gyro, using kinematics as fallback.", AlertType.kError);

        private final Timer lastMovementTimer = new Timer();

        private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(DriveConstants.moduleTranslations);

        @AutoLogOutput
        private boolean velocityMode = false;
        @AutoLogOutput
        private boolean brakeModeEnabled = true;

        private SwerveSetpoint currentSetpoint = new SwerveSetpoint(
                        new ChassisSpeeds(),
                        new SwerveModuleState[] {
                                        new SwerveModuleState(),
                                        new SwerveModuleState(),
                                        new SwerveModuleState(),
                                        new SwerveModuleState()
                        });
                        
        private final SwerveSetpointGenerator swerveSetpointGenerator;

        public Drive(
                        GyroIO gyroIO,
                        ModuleIO flModuleIO,
                        ModuleIO frModuleIO,
                        ModuleIO blModuleIO,
                        ModuleIO brModuleIO) {
                this.gyroIO = gyroIO;
                modules[0] = new Module(flModuleIO, 0);
                modules[1] = new Module(frModuleIO, 1);
                modules[2] = new Module(blModuleIO, 2);
                modules[3] = new Module(brModuleIO, 3);
                lastMovementTimer.start();
                setBrakeMode(true);

                swerveSetpointGenerator = new SwerveSetpointGenerator(kinematics, DriveConstants.moduleTranslations);

                // Start odometry thread
                PhoenixOdometryThread.getInstance().start();
        }
}