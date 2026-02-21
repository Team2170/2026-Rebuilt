package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.climber.Climber;

public class ClimberCommands {
    public Command Climb_L1(Climber climber) {
        return Commands.sequence(
            Commands.runOnce(new Command() {
            
        }, climber
        ));
    }
}

