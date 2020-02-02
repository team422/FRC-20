package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Helix extends Subsystem {

    public WPI_TalonSRX helicase;
    public DoubleSolenoid cellStop;
    public DigitalInput intakeBeamBreak;

    public Helix() {
        super("Helix");
        this.helicase = new WPI_TalonSRX(RobotMap.helicase);
        this.intakeBeamBreak = new DigitalInput(RobotMap.intakeBeamBreak);
        this.cellStop = new DoubleSolenoid(RobotMap.cellStopOut, RobotMap.cellStopIn);
    }
    
    protected void initDefaultCommand() {}

    public void setHelixMotors(double power) {
        helicase.set(ControlMode.PercentOutput, -power);
    }

    public boolean getCellEntered() {
        return !intakeBeamBreak.get();
    }

    public void cellStopOut() {
        cellStop.set(DoubleSolenoid.Value.kForward);
    }

    public void cellStopIn() {
        cellStop.set(DoubleSolenoid.Value.kReverse);
    }
}