package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class DriveStraight extends Command {

    private double ticks;
    private boolean forward;
    private double speed;

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
        double correction = Subsystems.driveBase.getGyroAngle();
        correction *= 0.075;
        correction += 1.0;
        if (forward) {
            Subsystems.driveBase.setMotors(-speed, -speed);
        } else {
            Subsystems.driveBase.setMotors(speed, speed);
        }
    }

    @Override
    public boolean isFinished() {
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

    public double convertToTicks(double inches) {
        return (4096 / (6 * 3.1415926) * inches);
    }

}