package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Flyboi extends Subsystem {

    private SparkMax leftFlywheel;
    private SparkMax rightFlywheel;

    public Flyboi() {
        super("Flyboi");
        this.leftFlywheel = new SparkMax(RobotMap.leftFlywheel);
        this.rightFlywheel = new SparkMax(RobotMap.rightFlywheel);
        this.rightFlywheel.setInverted(true);
    }

    public void initDefaultCommand() {}

    /**
     * Spins wheel motors.
     * @param speed The speed to set the flywheel to (-1 to 1).
     */
    public void spinWheel(double speed) {
        leftFlywheel.set(speed);
        rightFlywheel.set(speed);
    }

    /** Stops wheel motors. */
    public void stopWheel() {
        leftFlywheel.set(0);
        rightFlywheel.set(0);
    }
}