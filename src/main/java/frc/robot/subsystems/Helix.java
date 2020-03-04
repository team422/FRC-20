package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The helix, the storage mechanism, with a spinning brush and cell stop piston.
 */
public class Helix extends Subsystem {

    public WPI_TalonSRX helicase;
    public DoubleSolenoid cellStop;

    public int cellCount = 3;

    public Helix() {
        super("Helix");
        this.helicase = new WPI_TalonSRX(RobotMap.helicase);
        this.cellStop = new DoubleSolenoid(RobotMap.cellStopOut, RobotMap.cellStopIn);
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

    /**
     * Extends the cell stop.
     */
    public void cellStopOut() {
        cellStop.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Retracts the cell stop.
     */
    public void cellStopIn() {
        cellStop.set(DoubleSolenoid.Value.kReverse);
    }
}