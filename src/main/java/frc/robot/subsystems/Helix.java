package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The helix, the storage mechanism, with a spinning brush and cell stop piston.
 */
public class Helix extends Subsystem {

    public WPI_TalonSRX helicase;

    public int cellCount = 3;

    public Helix() {
        super("Helix");
        this.helicase = new WPI_TalonSRX(RobotMap.helicase);
    }

    protected void initDefaultCommand() {}

    /**
     * Spins the helix brush.
     * @param power The power with which to spin the brush (-1 to 1).
     */
    public void setHelixMotors(double power) {
        helicase.set(ControlMode.PercentOutput, power); //is -power for practice bot
    }

    /**
     * Stops the helix brush.
     */
    public void stopHelixMotors() {
        helicase.set(ControlMode.PercentOutput, 0);
    }
}