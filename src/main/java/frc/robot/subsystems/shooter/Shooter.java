package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.Constants;

public class Shooter extends SubsystemBase {
    
    private final ShooterModule shooterModule;
    private ShooterState shooterState;

    public Shooter(int shooterID, int followerID, CANBus canbus){

       // module = new ShooterModule(Constants.ShooterConstants.shooterID, Constants.ShooterConstants.followerID);

       shooterModule = new ShooterModule(shooterID, followerID, canbus);
        shooterState = new ShooterState(0); // default





    }

    public void setRPM(double revolutionsPerMinute) {
        shooterModule.setRevolutionsPerMinute(revolutionsPerMinute);
        
        shooterState = new ShooterState(revolutionsPerMinute);
    }

    public void stop() {
        shooterModule.stop();
        shooterState = new ShooterState(0);
    }

    public double getRevolutionsPerMinute() {
        //return    shooterModule.getRPM();

        return    shooterModule.getRevolutionsPerMinute();
    }

    public boolean atSetpoint() {
        return shooterModule.atSetPoint(shooterState.speed);
    }

    public boolean isSpeedSet() {
        return shooterState.isSpeedSet;
    }



}
