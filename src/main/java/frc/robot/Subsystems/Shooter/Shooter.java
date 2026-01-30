package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Constants;

public class Shooter extends SubsystemBase {
    
    private TalonFX shooter_motor;

    public boolean spinning;

    private final static double SPEED = 0.5;

    public Shooter(){

        shooter_motor = new TalonFX(Constants.ClimberConstants.climberMotorId, "rio"); // TODO: FIND SHOOTER ID
        spinning = false;

    }

    private void configMotors() {
        
    }

    public void spin(){

        DutyCycleOut request = new DutyCycleOut(spinning ? 0 : SPEED);
        shooter_motor.setControl(request.withOutput(spinning ? 0 : SPEED));
        spinning = spinning ? false : true;

    }


    


}
