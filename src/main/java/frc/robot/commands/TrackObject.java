package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.*;
/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackObject extends Command {   

    public TrackObject() {
        super("TrackObject");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
       
    }

    @Override
    public void execute() { 
        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        NetworkTable table = ntinst.getTable("cellTable");
        NetworkTableEntry correction = table.getEntry("correction");
        double correct = correction.getDouble(0);
        if (Math.abs(correct) > 0.25) {
            Subsystems.driveBase.setMotors(-0.25*correct, 0.25*correct);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0, 0);
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0, 0);
    }
}