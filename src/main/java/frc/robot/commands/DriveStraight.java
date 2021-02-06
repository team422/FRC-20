package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;

/**
 * Drives the robot in a straight line.
 */
public class DriveStraight extends CommandBase {

    private double ticks;
    private boolean forward;
    private double speed;

    /**
     * Drives the robot in a straight line.
     * @param Inches Distance forwards or backwards in inches.
     * @param Speed Speed at which the bot travels (0 to 1).
     */
    public DriveStraight(double Inches, double Speed) {
        setName("DriveStraight");
        addRequirements(Subsystems.driveBase);
        ticks = convertToTicks(Math.abs(Inches));
        forward = Inches > 0;
        speed = Speed;
    }

    public void initialize() {
        System.out.println("Starting driveStraight!");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }

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

    public boolean isFinished() {
        double leftPosition = Math.abs(Subsystems.driveBase.getLeftPosition());
        double rightPosition = Math.abs(Subsystems.driveBase.getRightPosition());
        return (leftPosition > ticks) || (rightPosition > ticks);
    }

    public void end(boolean interrupted) {
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