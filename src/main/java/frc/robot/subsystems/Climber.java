package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends Subsystem {

  public WPI_TalonSRX crankyCrank;
  public DoubleSolenoid pinPullerTop;
  public DoubleSolenoid pinPullerBottom;
  public DoubleSolenoid brakeyBrake;

  public Climber() {
      
    super("Climber");
    this.crankyCrank = new WPI_TalonSRX(RobotMap.crankyCrank);
    this.pinPullerTop = new DoubleSolenoid(RobotMap.pinPullerTopOut, RobotMap.pinPullerTopIn);
    this.pinPullerBottom = new DoubleSolenoid(RobotMap.pinPullerBottomOut, RobotMap.pinPullerBottomIn);
    this.brakeyBrake = new DoubleSolenoid(RobotMap.brakeyBrakeIn, RobotMap.brakeyBrakeOut);
  
  }

  public void initDefaultCommand() {}

  public void brakeRelease() {
    brakeyBrake.set(DoubleSolenoid.Value.kReverse);
  }
  
  public void brakeSet() {
    brakeyBrake.set(DoubleSolenoid.Value.kForward);
  }

  public void pullPins() {
    pinPullerTop.set(DoubleSolenoid.Value.kReverse);
    pinPullerBottom.set(DoubleSolenoid.Value.kReverse);
  }

  public void pushPins() {
    pinPullerTop.set(DoubleSolenoid.Value.kForward);
    pinPullerBottom.set(DoubleSolenoid.Value.kForward);
  }

  public void contractClimber() {
    crankyCrank.set(0.3);
  }

  public void stopClimber() {
    crankyCrank.set(0);
  }
}