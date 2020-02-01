package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Flyboi extends Subsystem {

  private WPI_TalonSRX leftFlywheel;
  private WPI_TalonSRX rightFlywheel;

  public Flyboi() {
    super("Flyboi");
    this.leftFlywheel = new WPI_TalonSRX(RobotMap.leftFlywheel);
    this.rightFlywheel = new WPI_TalonSRX(RobotMap.rightFlywheel);
    this.rightFlywheel.follow(this.leftFlywheel);
    this.rightFlywheel.setInverted(true);
  }

  public void initDefaultCommand() {}

  /**
   * Spins wheel motors.
   * @param speed The speed to set the flywheel to (0-1).
  */
  public void spinWheel(double speed) {
    leftFlywheel.set(speed); //right follows left
  }

  /** Stops wheel motors. */  
  public void stopWheel() {
    leftFlywheel.set(0); //right follows left
  }
}