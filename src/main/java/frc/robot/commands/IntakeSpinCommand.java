// package frc.robot.commands;
// import edu.wpi.first.wpilibj2.command.Command;
// //import edu.wpi.first.wpilibj2.command.CommandScheduler;
// //import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import frc.robot.subsystems.intake.Intake;

// public class IntakeSpinCommand extends Command{
//     private final Intake intake;
//     private final double speed;


//     public IntakeSpinCommand(Intake intakeSub, double intakeSpeed) {
//         this.intake = intakeSub;
//         addRequirements(intake);
//         this.speed = intakeSpeed;
//     }
    


//     @Override
//     public void initialize() {
     
//     }

//     @Override
//     public void execute(){
//         intake.setSpinPercentIn(speed); 
//     }

//     @Override
//     public void end(boolean interrupted) {
//         intake.stopSpin(); 
//     }

//     @Override
//     public boolean isFinished(){
//         return false;
//     }


// }




