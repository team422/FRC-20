package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.*;

/**
 * Turns the helix when the flywheel is running & up to speed. If this is executed in auto, it must have a timeout.
 */
public class CellVision extends CommandBase {

    NetworkTable table;
    NetworkTableEntry cellRunnerEntry;

    public CellVision() {
        setName("CellVision");
    }

    @Override
    public void initialize() {
        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        NetworkTable table = ntinst.getTable("visionTable");
        cellRunnerEntry = table.getEntry("CellVisionRunner");
        cellRunnerEntry.forceSetBoolean(true);
    }

    @Override
    public void execute() {
        NetworkTableEntry cellLateralTranslationEntry = table.getEntry("CellVisionLateralTranslation");
        NetworkTableEntry cellLongitudinalTranslationEntry = table.getEntry("CellVisionLongitudinalTranslation");
        NetworkTableEntry cellRotationEntry = table.getEntry("CellVisionRotation");
        System.out.print("cell lateral translation is " + cellLateralTranslationEntry.getDouble(-1));
        System.out.print("cell longitudinal translation is " + cellLongitudinalTranslationEntry.getDouble(-1));
        System.out.print("cell rotation is " + cellRotationEntry.getDouble(0));
    }

    @Override
    public void end(boolean interrupted) {        
        cellRunnerEntry.forceSetBoolean(false);
    }

}