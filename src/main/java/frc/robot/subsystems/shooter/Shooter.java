package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;

public class Shooter extends SubsystemBase {
    
    private TalonFX shooter_motor;

    public boolean spinning;

    private final static double SPEED = 0.5;

    public Shooter(){}

    private void configMotors() {}

    public void spin(){}


    


}
