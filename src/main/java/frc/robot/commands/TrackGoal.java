package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;
// import edu.wpi.first.networktables.*;

/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackGoal extends CommandBase {

    public TrackGoal() {
        setName("TrackGoal");
        addRequirements(Subsystems.driveBase);
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
    public void end(boolean interrupted) {
        Subsystems.driveBase.setMotors(0, 0);
        Subsystems.driveBase.ringLightOff();
    }
}