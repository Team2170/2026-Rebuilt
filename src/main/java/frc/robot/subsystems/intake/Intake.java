package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.components.IntakeIO;
import frc.robot.subsystems.intake.components.IntakeIOInputsAutoLogged;

public class Intake extends SubsystemBase{
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private final String SubystemName;

    public Intake(String name, IntakeIO io) {
        this.io = io;
        this.SubystemName = name;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(SubystemName, inputs);
    }

    public void setIntakePower(double percent) {
        io.setIntakePower(percent);
    }

    public void stopIntake() {
        io.stopIntake();
    }
    
}
