package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;

/**
 * Retracts the cell stop.
 */
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
        RobotMap.isCellStopUp = true;
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