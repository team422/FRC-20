package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * A command to toggle between the intake extended and the intake retracted.
 */
public class CellStopExtendRetract extends Command {

    public CellStopExtendRetract() {
        super("CellStopExtendRetract");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        if (RobotMap.isCellyToggled) {
            Subsystems.helix.cellStopIn();
			RobotMap.isCellyToggled = false;
        } else {
            Subsystems.helix.cellStopOut();
            RobotMap.isCellyToggled = true;
        }
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
    }

    @Override
    public void end() {
    }
}