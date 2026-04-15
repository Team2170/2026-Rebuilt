package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.Climber;

public class ClimberCommands extends Command{
    public Climber ClimberSubsystem;
    public double ClimberRotations;

    public ClimberCommands(Climber climber, double rotations){
        ClimberSubsystem = climber;
        ClimberRotations = rotations;
        addRequirements(climber);

    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        ClimberSubsystem.setPosition(ClimberRotations);
    }

    @Override
    public void end(boolean interrupted){
        ClimberSubsystem.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }





}