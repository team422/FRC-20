package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends Subsystem {

    public WPI_TalonSRX climberWinch;
    public DoubleSolenoid leftClimbPin;
    public DoubleSolenoid rightClimbPin;
    public DoubleSolenoid climberBrake;

    public Climber() {
        super("Climber");
        this.climberWinch = new WPI_TalonSRX(RobotMap.climberWinch);
        // this.leftClimbPin = new DoubleSolenoid(RobotMap.leftClimbPinOut, RobotMap.leftClimbPinIn);
        // this.rightClimbPin = new DoubleSolenoid(RobotMap.rightClimbPinOut, RobotMap.rightClimbPinIn);
        // this.climberBrake = new DoubleSolenoid(RobotMap.climberBrakeIn, RobotMap.climberBrakeOut);
    }

    public void initDefaultCommand() {}

    public void brakeRelease() {
        climberBrake.set(DoubleSolenoid.Value.kReverse);
    }

    public void brakeDown() {
        climberBrake.set(DoubleSolenoid.Value.kForward);
    }

    public void pullPins() {
        leftClimbPin.set(DoubleSolenoid.Value.kReverse);
        rightClimbPin.set(DoubleSolenoid.Value.kReverse);
    }

    public void pushPins() {
        leftClimbPin.set(DoubleSolenoid.Value.kForward);
        rightClimbPin.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Retracts the climber at the specified percentage of speed.
     * @param speed The percent of speed (disregarding speed cap), from -1 to 1.
     */
    public void contractClimber(double speed) {
        climberWinch.set(speed);
    }

    public void stopClimber() {
        climberWinch.set(0);
    }
}