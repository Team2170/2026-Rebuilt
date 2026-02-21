package frc.robot.commands;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.simulation.SendableChooserSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.Constants;
import frc.robot.subsystems.shooter.Shooter;

public class Shoot extends Command{
    CommandXboxController controller;
    Shooter shooter;

    public Shoot(CommandXboxController controller, Shooter shooter) {
        this.controller = controller;
        this.shooter = shooter;
        addRequirements(shooter);
    }

    public void initialize() {
        // if shooter is not shooting, activate it. if shooting, turn it off
        shooter.setShooting(!shooter.isShooting());
    }

    public void execute() {
        shooter.setPercentOut(shooter.isShooting() ? Constants.ShooterConstants.SHOOTING_SPEED : 0);
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {

    }
}
