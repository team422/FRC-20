package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;

public class Intake extends Subsystem {

    public WPI_TalonSRX intake;
    public DoubleSolenoid intakeExtension;

    public Intake() {
        super("Intake");
        this.intake = new WPI_TalonSRX(RobotMap.intake);
        this.intakeExtension = new DoubleSolenoid(RobotMap.intakeExtensionOut, RobotMap.intakeExtensionIn);
    }

    protected void initDefaultCommand() {}

    public void setIntakeMotors(double power) {
        intake.set(ControlMode.PercentOutput, -power);
    }    public void stopIntakeMotors() {
        intake.set(ControlMode.PercentOutput, 0.0);
    }

    public void intakeExtend() {
        intakeExtension.set(DoubleSolenoid.Value.kForward);
    }

    public void intakeRetract() {
        intakeExtension.set(DoubleSolenoid.Value.kReverse);
    }
}