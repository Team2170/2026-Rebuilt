package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    private TalonFX shooter_motor;


    public Shooter(){

        shooter_motor = new TalonFX(0);


    }

    public void spin(){

        shooter_motor.set(0.2);
        System.out.println("Motor spinning ");

    }


}
