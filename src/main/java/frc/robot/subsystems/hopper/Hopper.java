package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.hopper.components.HopperIO;
import frc.robot.subsystems.hopper.components.HopperIOInputsAutoLogged;

public class Hopper extends SubsystemBase {
    private final HopperIO io;
    private final HopperIOInputsAutoLogged inputs = new HopperIOInputsAutoLogged();
    private final String SubystemName;

    public Hopper(String name, HopperIO io) {
        this.io = io;
        this.SubystemName = name;
    }

    /**
     * Periodic method called once per scheduler run. Updates sensor inputs and
     * maintains position when necessary.
     */
    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(SubystemName, inputs);
    }

    public void setHopperPower(double percent) {
        io.setHopperPower(percent);
    }

    // public void setIntakePower(double percent) {
    //     io.setIntakePower(percent);
    // }

    public void stopHopper() {
        io.stopHopper();
    }

    public void extendHopper() {
        io.extendHopper();
    }

    public void retractHopper() {
        io.retractHopper();
    }

    // public void stopIntake() {
    //     io.stopIntake();
    // }
}
