package frc.robot.Subsystems.Shooter;

public class ShooterState {

  
    public double speed;
    
    public boolean isSpeedSet = false;

    public ShooterState(double speed) {
        this.speed = speed;
        this.isSpeedSet = true;
    }


}
