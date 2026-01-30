package frc.robot.Subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
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


    public Encoder relFlywheelEncoderLeft;
    public Encoder relFlywheelEncoderRight;



    public ShooterModule(int shooterID, int followerID) {
        shooterId = shooterID;
        followerId = followerID;
        /* Shooter Motor Config */
        //mShooterMotor = new TalonFX(shooterID, 1);
       // mShooterFollower = new TalonFX(followerID, 1);
        configFlywheelMotor();
       // relFlywheelEncoderLeft = new Encoder(mShooterMotor);
        //relFlywheelEncoderRight = new Encoder(mShooterFollower);
    }


    public void configFlywheelMotor(){





    }




    




}
