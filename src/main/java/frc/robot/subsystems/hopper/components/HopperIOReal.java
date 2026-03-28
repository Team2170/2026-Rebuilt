package frc.robot.subsystems.hopper.components;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.constants.Constants.HopperConstants;

public class HopperIOReal {
    private TalonFX MasterHopperMotor;
    private TalonFX FollowerHopperMotor;
    public static final double HOPPER_MOTOR_RATIO = 1;
    private DutyCycleOut request;

    public HopperIOReal() {
        MasterHopperMotor = new TalonFX(HopperConstants.MasterHopperMotorID);
        FollowerHopperMotor = new TalonFX(HopperConstants.FollowerHopperMotorID);
        configMotors();

        request = new DutyCycleOut(0), withEnableFOC(true);
    }

    public void configMotors() {
        MasterHopperMotor.getConfigurator().apply(new TalonFXConfiguration());
        FollowerHopperMotor.getConfigurator().apply(new TalonFXConfiguration());

        MasterHopperMotor.setInverted(false);
        MasterHopperMotor.setNeutralMode(TalonFX.NeutralMode.Coast);
        MasterHopperMotor.getSensorCollection().setQuadraturePosition(0, 0);
        MasterHopperMotor.getSensorCollection().setQuadratureVelocity(0, 0);
        MasterHopperMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
        MasterHopperMotor.getSensorCollection().setIntegratedSensorVelocity(0, 0);

        FollowerHopperMotor.follow(MasterHopperMotor);
    }
}
