package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.*;

/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackObject extends CommandBase {

    NetworkTable table;
    NetworkTableEntry cellRunnerEntry;
    NetworkTableEntry cellDistanceEntry;
    NetworkTableEntry cellRotationEntry;


    public TrackObject() {
        setName("TrackObject");
        addRequirements(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();

        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        NetworkTable table = ntinst.getTable("visionTable");
        cellRunnerEntry = table.getEntry("CellVisionRunner");
        cellRunnerEntry.forceSetBoolean(true);
        cellRotationEntry = table.getEntry("CellVisionRotation");
        cellDistanceEntry = table.getEntry("CellVisionDistance");
    }

    @Override
    public void execute() { 
        System.out.print("cell rotation is " + cellRotationEntry.getDouble(0));
    }

    @Override
    public void end(boolean interrupted) {
        Subsystems.driveBase.setMotors(0, 0);
    }
}