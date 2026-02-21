package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hopper.Hopper;

public class HopperCommands extends Command{
    private final Hopper hopperSubsystem;

    public HopperCommands(Hopper subsystem){
        hopperSubsystem = subsystem;
        addRequirements(hopperSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        //change to scale correctly
        hopperSubsystem.setPercentOut(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        hopperSubsystem.stop();
    }
}
