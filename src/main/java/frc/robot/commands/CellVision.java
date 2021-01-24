package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.networktables.*;

/**
 * Turns the helix when the flywheel is running & up to speed. If this is executed in auto, it must have a timeout.
 */
public class CellVision extends Command {


    public CellVision() {
        super("CellVision");
    }

    @Override
    protected void initialize() {
        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        NetworkTable table = ntinst.getTable("visionTable");
        NetworkTableEntry cellRunnerEntry = table.getEntry("CellVisionRunner");
        cellRunnerEntry.forceSetBoolean(true);
    }

    @Override
    protected void execute() {
        NetworkTableEntry cellLateralTranslationEntry = table.getEntry("CellVisionLateralTranslation");
        NetworkTableEntry cellLongitudinalTranslationEntry = table.getEntry("CellVisionLongitudinalTranslation");
        NetworkTableEntry cellRotationEntry = table.getEntry("CellVisionRotation");
        System.out.print("cell lateral translation is " + cellLateralTranslationEntry.getdouble(-1));
        System.out.print("cell longitudinal translation is " + cellLongitudinalTranslationEntry.getdouble(-1));
        System.out.print("cell rotation is " + cellRotationEntry.getdouble(0));
    }

    @Override
    protected void interrupted() {
        cellRunnerEntry.forceSetBoolean(false);
    }
        
    @Override
    protected void end() {        
        cellRunnerEntry.forceSetBoolean(false);
    }

}