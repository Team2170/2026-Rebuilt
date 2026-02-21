
package frc.robot.subsystems.intake;


//Imports
import frc.robot.constants.Constants;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
//import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
//import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
//import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase{
   private TalonFX spinMotor;
   private TalonFX liftMotor;
   private DutyCycleOut request;




   //Constructor with spin and intake motor
    public Intake(){
       spinMotor = new TalonFX(Constants.IntakeConstants.SpinMotorId, Constants.canbus);
       liftMotor = new TalonFX(Constants.IntakeConstants.LiftMotorId, Constants.canbus);
       


       //Assorted Configs
       TalonFXConfiguration spinConfig = new TalonFXConfiguration();
       TalonFXConfiguration liftConfig = new TalonFXConfiguration();


       spinConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
       liftConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
      

        liftConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        liftConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;   //Bottom position CHANGE THIS!!

        liftConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        liftConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 50;  //CHANGE to your real max rotations

        spinMotor.getConfigurator().apply(spinConfig);
        liftMotor.getConfigurator().apply(liftConfig);

        //Zero lift encoder at startup (important for soft limits)
        liftMotor.setPosition(0);


       spinMotor.setNeutralMode(NeutralModeValue.Brake);
       liftMotor.setNeutralMode(NeutralModeValue.Brake);




       //current limiting from the battery for spin motor
       spinConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
       spinConfig.CurrentLimits.SupplyCurrentLimit = 40;




       //current limiting from the battery for lift motor
       liftConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
       liftConfig.CurrentLimits.SupplyCurrentLimit = 30;


       spinMotor.getConfigurator().apply(spinConfig);
       liftMotor.getConfigurator().apply(liftConfig);
  
   }




    public void stopSpin(){
       spinMotor.stopMotor();
    }


    public void stopIntakeMechanism(){
       liftMotor.stopMotor();
    }


    //Actual intake function/can also shoot out balls with negative values (range from -1 to 1)
    public void setSpinPercentIn(double percent) {
        percent = Math.max(-1.0, Math.min(1.0, percent));
        spinMotor.setControl(request.withOutput(percent));
    }




    //Motor that lifts up the mechanism
    public void setLiftPercentOut(double percent) {
        percent = Math.max(-1.0, Math.min(1.0, percent));
        liftMotor.setControl(request.withOutput(percent));
    }


}
