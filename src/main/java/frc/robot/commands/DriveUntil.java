package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;
// import java.sql.Time;

/**
 * A Command to drive the robot in a straight line until it reaches a specified distance from a surface.
 */
public class DriveUntil extends Command {

    private double ticks;
    private boolean forward;
    private double speed;
    private double finalInches;
    private double distanceTo;

    /**
     * Drives the robot in a straight line until it reaches a specified distance from a surface.
     * @param finalInches Distance away from surface that should be reached.
     * @param Speed Speed at which the bot travels (0 to 1).
     * @param Timeout The timeout, in seconds.
     */
    public DriveUntil(double FinalInches, double Speed, double Timeout) {
        super("DriveUntil");
        requires(Subsystems.driveBase);

        distanceTo = Subsystems.ultrasonic.getInchesAway() - FinalInches;
        ticks = convertToTicks(Math.abs(distanceTo));
        forward = distanceTo > 0;
        speed = Speed;

        if (FinalInches < 11.2) {
            System.out.println("Given distance is too close for proper detection!");
            setTimeout(0.01);
        } else {
            setTimeout(Timeout);
        }
    }

    @Override
    public void initialize() {
        System.out.println("Starting driveUntil!");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }

    @Override
    public void execute() {
        if (forward) {
            Subsystems.driveBase.setMotors(-speed, -speed);
        } else {
            Subsystems.driveBase.setMotors(speed, speed);
        }
    }

    @Override
    public boolean isFinished() {
        int leftPosition = Math.abs(Subsystems.driveBase.getLeftPosition());
        int rightPosition = Math.abs(Subsystems.driveBase.getRightPosition());
        boolean tooFar = false;
        boolean blindZone = false;
        if (finalInches>Subsystems.ultrasonic.getInchesAway()&&forward) {
            tooFar = true;
        }
        else if (finalInches<Subsystems.ultrasonic.getInchesAway()&&!forward) {
            tooFar = true;
        }
        if (Subsystems.ultrasonic.getInchesAway()<=11.2&&forward) {
            //blindZone = true; //Disabled for now, will enable if it's a problem
        }
        return (leftPosition > ticks) || (rightPosition > ticks) || isTimedOut() || tooFar || blindZone;
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