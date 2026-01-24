package frc.robot.Subsystems.Shooter;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterModule {
    public int shooterId;
    public int followerId;
    private TalonFX mShooterMotor; // correct (thanks farhan)
    private TalonFX mShooterFollower;
    public CANcoder relFlywheelEncoderLeft;
    public CANcoder relFlywheelEncoderRight;

    public ShooterModule(int shooterID, int followerID) {
        shooterId = shooterID;
        followerId = followerID;
        /* Shooter Motor Config */
        mShooterMotor = new TalonFX(shooterId,"cancoder"); // TODO: CONFIGURE TO PHOENIX TUNER NAMES
        mShooterFollower = new TalonFX(followerId); // TODO: CONFIGURE TO PHOENIX TUNER NAMES
        configFlywheelMotor(); // TODO: FIX
        relFlywheelEncoderLeft = new encoder(mShooterMotor); // TODO: FIX
        relFlywheelEncoderRight = new encoder(mShooterFollower); // TODO: FIX
    }

    /**
     * Configs Roller Motor
     */
    private void configFlywheelMotor() {
        // Set Up Master Motor
    }


    /**
     * @param desiredState
     * @param isOpenLoop
     *                     Sets the desired state of the module.
     */
    public void setDesiredState(ShooterState state, boolean isOpenLoop) { // TODO: SHOOTERSTATE
        
    }

    /**
     * @param desiredState
     * @param isOpenLoop
     *                     given an state of the Shooter ( speed of rollers , and
     *                     angle of the pivot) update the speed(velocity) of the
     *                     rollers
     */
    private void setSpeed(ShooterState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speed;
            if(percentOutput > 1){
                percentOutput = 1;
            }
            mShooterMotor.set(percentOutput);
            mShooterFollower.set(percentOutput);
            return;
        }
        double velocity = desiredState.speed;
        SparkPIDController controller = mShooterMotor.getPIDController();
        controller.setReference(velocity, ControlType.kVelocity, 0);
        controller = mShooterFollower.getPIDController();
        controller.setReference(velocity, ControlType.kVelocity, 0);
    }

    /**
     * @return Shooter given module velocity and angle.
     */
    public ShooterState getState() {
        return new ShooterState(
                relFlywheelEncoderLeft.getVelocity());
    }

    /**
     * @return Shooter given module averaged shooter flywheels velocity and angle.
     */
    public ShooterState getSpeed() {
        double avg = (relFlywheelEncoderLeft.getVelocity() + relFlywheelEncoderRight.getVelocity() ) / 2;
        SmartDashboard.putNumber("Shooter/Left Velocity (rpm)", relFlywheelEncoderLeft.getVelocity());
        SmartDashboard.putNumber("Shooter/Right Velocity (rpm)", relFlywheelEncoderRight.getVelocity());
        
        return new ShooterState(avg);
    }

    public void stopRollers() {
        mShooterMotor.stopMotor();
        mShooterFollower.stopMotor();
    }
}