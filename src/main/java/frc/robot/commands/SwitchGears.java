package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * Toggles between slow and fast mode.
 */
public class SwitchGears extends Command {

    public SwitchGears() {
        super("SwitchGears");
    }

    @Override
    protected void execute() {
        if (RobotMap.isSpeedMode) {
            RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
            RobotMap.isSpeedMode = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(0.8, 0.7);
            RobotMap.isSpeedMode = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}