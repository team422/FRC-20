package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.RobotMap;

public class DriveStraightIdeal extends Command {

  private double ticks;
  private double speed;
  private boolean forward;

  public DriveStraightIdeal(double Speed, double Timeout) {
    super("DriveStraightIdeal");
    requires(Subsystems.driveBase);
    speed = Speed;
    setTimeout(Timeout);                 
  }

  @Override
  protected void initialize() {
    ticks = convertToTicks(RobotMap.getDriveOffset());
    if (RobotMap.getDriveOffset() >= 0) {
      forward = false;
    } else {
      forward = true;
    }   
    Subsystems.driveBase.zeroEncoderPosition();
    Subsystems.driveBase.zeroGyroAngle();
  }

  @Override
  protected void execute() {
    double correction = Subsystems.driveBase.getGyroAngle();
    correction *= 0.075;
    correction += 1.0;
    if (forward) {
        Subsystems.driveBase.setMotors(speed * correction, speed);
    } else {
        Subsystems.driveBase.setMotors(-speed, -speed * correction);
    }
  }

  @Override
  protected boolean isFinished() {
    int leftPosition = Math.abs(Subsystems.driveBase.getLeftPosition());
    int rightPosition = Math.abs(Subsystems.driveBase.getRightPosition());
    return (leftPosition > ticks) || (rightPosition > ticks) || isTimedOut();
  }

  @Override
  protected void interrupted() {
    Subsystems.driveBase.stopMotors();
  }
  
  @Override
  protected void end() {
    Subsystems.driveBase.stopMotors();
  }

  public double convertToTicks(double inches) {
    return (4096 / (6 * 3.1415926) * inches);
  }

}