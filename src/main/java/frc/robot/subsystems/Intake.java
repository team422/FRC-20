package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;

/**
 * The intake. Includes intake motors and pistons for intake extension.
 */
public class Intake extends Subsystem {
    public WPI_TalonSRX intakeMotor;
    public DoubleSolenoid intakeExtension;
    public DigitalInput intakeBeamBreak;
    public int cellCount = 3;

    public Intake() {
        super("Intake");
        this.intakeMotor = new WPI_TalonSRX(RobotMap.intakeMotor);
        this.intakeBeamBreak = new DigitalInput(RobotMap.intakeBeamBreak);
        this.intakeExtension = new DoubleSolenoid(RobotMap.intakeExtensionOut, RobotMap.intakeExtensionIn);
    }

    protected void initDefaultCommand() {}

    /**
     * Spins intake motors.
     * @param power The power at which the intake motors are set [-1 to 1].
     */
    public void setIntakeMotors(double power) {
        intakeMotor.set(ControlMode.PercentOutput, power);
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
        System.out.println("Going down");
    }

    /**
     * Retracts the intake (intake up).
     */
    public void intakeRetract() {
        intakeExtension.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Going up");
    }

    /**
     * @return Whether the intake beam break currently detects something.
     */
    public boolean getCellEntered() {
        return !intakeBeamBreak.get();
    }
}