package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;

/**
 * Drives the robot in a straight line.
 */
public class DriveStraight extends Command {

    private double ticks;
    private boolean forward;
    private double speed;

    /**
     * Drives the robot in a straight line.
     * @param Inches Distance forwards or backwards in inches.
     * @param Speed Speed at which the bot travels (0 to 1).
     * @param Timeout The timeout, in seconds.
     */
    public DriveStraight(double Inches, double Speed, double Timeout) {
        super("DriveStraight");
        requires(Subsystems.driveBase);
        ticks = convertToTicks(Math.abs(Inches));
        forward = Inches > 0;
        speed = Speed;
        setTimeout(Timeout);
    }

    @Override
    public void initialize() {
        System.out.println("Starting driveStraight!");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }

    @Override
    public void execute() {
        double correction = Subsystems.driveBase.getGyroAngle();
        correction *= 0.05;
        correction += 1.0;
        if (forward) {
            Subsystems.driveBase.setMotors(-speed, -speed * correction);
        } else {
            Subsystems.driveBase.setMotors(speed * correction, speed);
        }
    }

    @Override
    public boolean isFinished() {
        int leftPosition = Math.abs(Subsystems.driveBase.getLeftPosition());
        int rightPosition = Math.abs(Subsystems.driveBase.getRightPosition());
        return (leftPosition > ticks) || (rightPosition > ticks) || isTimedOut();
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0,0);
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0,0);
    }

    /**
     * @param inches Inches to convert.
     * @return The equivalent distance in ticks.
     */
    public double convertToTicks(double inches) {
        return (4096 / (RobotMap.wheelDiameter * 3.1415926) * inches);
    }

}