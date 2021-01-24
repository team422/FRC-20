package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.*;

/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackObject extends CommandBase {

    public TrackObject() {
        setName("TrackObject");
        addRequirements(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
       
    }

    @Override
    public void execute() { 
        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        NetworkTable table = ntinst.getTable("visionTable");

        NetworkTableEntry testEntry = table.getEntry("test");
        double test = testEntry.getDouble(-1);
        System.out.println("test is " + test);

        // NetworkTableEntry correctionEntry = table.getEntry("correction");
        // double correction = correctionEntry.getDouble(-1);
        // if (Math.abs(correction) > 0.25) {
        //     Subsystems.driveBase.setMotors(-0.25*correction, 0.25*correction);
        // }
    }

    @Override
    public void end(boolean interrupted) {
        Subsystems.driveBase.setMotors(0, 0);
    }
}