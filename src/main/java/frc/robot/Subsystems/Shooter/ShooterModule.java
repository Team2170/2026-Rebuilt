package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter.ShooterState;

*/

public class ShooterModule {

    
    

    private TalonFX shooterMotor;
    private TalonFX shooterFollower;


    private static final double kP = 0;
    private static final double kI = 0;
    private static final double kD = 0;
    private static final double kS = 0;
    private static final double kV = 0;

    private static final double MAX_RPM =  0;
    private static final double RPM_TOLERANCE = 0;

    private final VelocityVoltage velocityRequest = new VelocityVoltage(0);


    public ShooterModule(int shooterID, int followerID, CANBus canbus) {
       
        
        shooterMotor = new TalonFX(shooterID, canbus);
        shooterFollower = new TalonFX(followerID, canbus);


        configFlywheelMotor();


    }


    public void configFlywheelMotor(){


        TalonFXConfiguration config = new TalonFXConfiguration();
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;


        shooterMotor.getConfigurator().apply(config);
        shooterFollower.getConfigurator().apply(config);

        
    }




    public void stopRollers() {

        shooterMotor.stopMotor();
        shooterFollower.stopMotor();

    }



    
    public void stop() {
        shooterMotor.stopMotor();
    }

    public void setRevolutionsPerMinute(double rpm) {
        rpm = Math.min(rpm, MAX_RPM);
        shooterMotor.setControl(
            velocityRequest.withVelocity(revolutionsPerMinuteToRevolutionsPerSecond(rpm))
        );
    }

    

    public double getRevolutionsPerMinute() {
        
        double revolutionsPerSecond = shooterMotor.getVelocity().getValueAsDouble();

        double revolutionsPerMinute = revolutionsPerSecond * 60.0;
        SmartDashboard.putNumber("Shooter RPM", revolutionsPerMinute);
        return revolutionsPerMinute;
    }

    public boolean atSetPoint(double targetRPM) {
        return Math.abs(getRevolutionsPerMinute() - targetRPM) <= RPM_TOLERANCE;
    }

    

    private double revolutionsPerMinuteToRevolutionsPerSecond(double rpm) {
        return rpm / 60.0;
    }


}
