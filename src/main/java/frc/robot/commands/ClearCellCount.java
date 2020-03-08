package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ClearCellCount extends Command {

    public ClearCellCount() {
        super("ClearCellCount");
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.helix.cellCount = 0;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}
}