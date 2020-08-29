package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.*;

/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackGoal extends Command {

    public TrackGoal() {
        super("TrackGoal");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
       
    }

    @Override
    public void execute() { 
        Subsystems.driveBase.ringLightOn();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0, 0);
        Subsystems.driveBase.ringLightOff();
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0, 0);
        Subsystems.driveBase.ringLightOff();
    }
}