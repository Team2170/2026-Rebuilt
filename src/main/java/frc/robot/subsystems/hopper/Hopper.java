package frc.robot.subsystems.hopper;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;


public class Hopper extends SubsystemBase{
    private TalonFX HopperMotor;
    private DutyCycleOut request;

    /**Constructor. */
    public Hopper(){
        HopperMotor = new TalonFX(Constants.HopperConstants.HopperMotorId);
        request = new DutyCycleOut(0).withEnableFOC(true);
    } 

    /** Configures the motor with the provided parameters. */
    public void configMotor() {
        TalonFXConfiguration internalConfig = new TalonFXConfiguration();

        /** Might have to change direction later. */
        internalConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);

        internalConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
        internalConfig.CurrentLimits.withStatorCurrentLimit(120);
        internalConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
    

        HopperMotor.getConfigurator().apply(internalConfig);
    }

    /** Basically makes the motor spin. */
    public void setPercentOut(double percent) {
        HopperMotor.setControl(request.withOutput(percent));
    }

    /** Stops the hopper motor. */
    public void stop(){
        HopperMotor.stopMotor();
    }

    @Override
    public void periodic() {
    }
}
