package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;

/**
 * The intake. Includes intake motors and pistons for intake extension.
 */
public class Intake extends Subsystem {

    public WPI_TalonSRX intakeMotor;
    public DoubleSolenoid intakeExtension;

    public Intake() {
        super("Intake");
        this.intakeMotor = new WPI_TalonSRX(RobotMap.intakeMotor);
        this.intakeExtension = new DoubleSolenoid(RobotMap.intakeExtensionOut, RobotMap.intakeExtensionIn);
    }

    protected void initDefaultCommand() {}

    /**
     * Spins intake motors.
     * @param power The power at which the intake motors are set.
     */
    public void setIntakeMotors(double power) {
        intakeMotor.set(ControlMode.PercentOutput, -power);
    }

    /**
     * Stops intake motors.
     */
    public void stopIntakeMotors() {
        intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }

    /**
     * Extends the intake (intake down).
     */
    public void intakeExtend() {
        intakeExtension.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Retracts the intake (intake up).
     */
    public void intakeRetract() {
        intakeExtension.set(DoubleSolenoid.Value.kReverse);
    }
}