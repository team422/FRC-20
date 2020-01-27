package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class DriveStraight extends Command {

    private double ticks;
    private boolean forward;
    private double speed;
    private boolean hasZeroed = false;

    /**
     * Drives the robot in a straight line.
     * @param inches Distance forwards or backwards in inches.
     * @param Speed Speed at which the bot travels (0 to 1).
     * @param Timeout The timeout, in seconds.  
     */
    public DriveStraight(double inches, double Speed, double Timeout) {
        super("DriveStraight");
        requires(Subsystems.driveBase);
        ticks = convertToTicks(Math.abs(inches));
        forward = inches > 0;
        speed = Speed;
    }

    @Override
    public void initialize() {
        System.out.println("Starting driveStraight!");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }

    @Override
    public void execute() {
        if (!hasZeroed) {
            if (Math.abs(Subsystems.driveBase.getLeftPosition()) < 150 && Math.abs(Subsystems.driveBase.getRightPosition()) < 150) {
              hasZeroed = true;
            } else {
              return;
            }
        }
          
        // double correction = Subsystems.driveBase.getGyroAngle();
        // correction *= 0.075;
        // correction += 1.0;
        if (forward) {
            Subsystems.driveBase.setMotors(-speed, -speed);
        } else {
            Subsystems.driveBase.setMotors(speed, speed);
        }
    }

    @Override
    public boolean isFinished() {
        if (!hasZeroed) {
            return false;
        }
          
        int leftPosition = (int) Math.abs(Subsystems.driveBase.getLeftPosition());
        int rightPosition = (int) Math.abs(Subsystems.driveBase.getRightPosition());
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
        return (4096 / (6 * 3.1415926) * inches);
    }

}