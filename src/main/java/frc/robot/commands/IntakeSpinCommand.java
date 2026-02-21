

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.intake.Intake;


public class IntakeSpinCommand extends Command{
   private final Intake intake;
   private final double speed;




   public IntakeSpinCommand(Intake intakeSub, double intakeSpeed) {
       this.intake = intakeSub;
       addRequirements(intake);
       this.speed = intakeSpeed;
   }
  




   @Override
   public void initialize() {
       intake.setSpinPercentIn(0.7); //change this later
   }


   @Override
   public void execute(){
       intake.setSpinPercentIn(speed);
   }


   @Override
   public void end(boolean interrupted) {
       intake.stopIntakeMechanism();
   }


   @Override
   public boolean isFinished(){
       return false;
   }




}




