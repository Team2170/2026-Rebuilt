package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;


public class IntakeLiftCommand extends Command {
   private final Intake intake;


   //Positive = lift up, Negative = lift down
   private final double speed;


   public IntakeLiftCommand(Intake intakeSub, double speed) {
       this.intake = intakeSub;
       this.speed = speed;


       //Still requires the Intake subsystem
       addRequirements(intake);
   }


   @Override
   public void execute() {
       //Control lift motor
       intake.setLiftPercentOut(speed);
   }


   @Override
   public void end(boolean interrupted) {
       //Stop lift when command ends
       intake.stopIntakeMechanism();
   }


   @Override
   public boolean isFinished() {
       //Runs until canceled
       return false;
   }
}
