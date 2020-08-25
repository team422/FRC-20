package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ClearCellCount extends Command {

    public ClearCellCount() {
        super("ClearCellCount");
    }

    @Override
    protected void execute() {
        Subsystems.helix.cellCount = 0;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}