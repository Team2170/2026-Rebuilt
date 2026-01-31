package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter.ShooterState;

*/

public class ShooterModule {

    
    private int shooterId;
    private int followerId;

    private TalonFX mShooterMotor;
    private TalonFX mShooterFollower;


    private static final double kP = 0;
    private static final double kI = 0;
    private static final double kD = 0;
    private static final double kS = 0;
    private static final double kV = 0;

    private static final double MAX_RPM =  0;
    private static final double RPM_TOLERANCE = 0;


    public ShooterModule(int shooterID, int followerID, CANBus canbus) {
        this.shooterId = shooterID;
        this.followerId = followerID;
        
        mShooterMotor = new TalonFX(shooterID, canbus);
        mShooterFollower = new TalonFX(followerID, canbus);


        configFlywheelMotor();


    }


    public void configFlywheelMotor(){


        TalonFXConfiguration config = new TalonFXConfiguration();
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;


        mShooterMotor.getConfigurator().apply(config);
        mShooterFollower.getConfigurator().apply(config);

   
        mShooterFollower.setControl(new Follower(shooterId, null));

       

        
    }




    public void stopRollers() {

        mShooterMotor.stopMotor();
        mShooterFollower.stopMotor();

    }

    


    public void setRPM(double rpm) {

        rpm = Math.min(rpm, MAX_RPM);

        mShooterMotor.setControl(velocityRequest.withVelocity(rpmToRotationsPerSecond(rpm)));



    }

    public ShooterState getSpeed() {
        double avg = (relFlywheelEncoderLeft.getVelocity() + relFlywheelEncoderRight.getVelocity() ) / 2;
        SmartDashboard.putNumber("Shooter/Left Velocity (rpm)", relFlywheelEncoderLeft.getVelocity());
        SmartDashboard.putNumber("Shooter/Right Velocity (rpm)", relFlywheelEncoderRight.getVelocity());


        

        

        

        
        
        return new ShooterState(avg);
    }


}
