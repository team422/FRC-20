package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

public class ClearCellCount extends CommandBase {

    public ClearCellCount() {
        setName("ClearCellCount");
    }

    public void execute() {
        Subsystems.helix.cellCount = 0;
    }

    public boolean isFinished() {
        return true;
    }

}