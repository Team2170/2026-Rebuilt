package frc.robot.Subsystems.Climber;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private final ClimberIO io;
    private boolean on;

 public Climber(String name, ClimberIO io) {
    this.io = io;
    this.on = false;
  }


/**
   * Periodic method called once per scheduler run. Updates sensor inputs and maintains position
   * when necessary.
   */
  @Override
  public void periodic() { 
  }

  public void changeState() {
    if (on) {
        io.setPercentOut(0); on = false;
    }
    else {io.setPercentOut(.5); on = true;}
  }
  public void test()  {
    
    ((ClimberIOReal) io).test();
  }

  /** Stops the climber, setting the otput to zero and maintaining position. */
  public void stop() {
    io.stop();
  }
}