package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CellStopRetract extends Command {

    public CellStopRetract() {
        super("CellStopRetract");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.helix.cellStopIn();
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