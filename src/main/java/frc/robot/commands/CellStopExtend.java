package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CellStopExtend extends Command {

    public CellStopExtend() {
        super("CellStopExtend");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.helix.cellStopOut();
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