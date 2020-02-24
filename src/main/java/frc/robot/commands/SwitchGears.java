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
    public void initialize() {
        if (RobotMap.isSpeedMode) {
            RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
            RobotMap.isSpeedMode = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(0.8, 0.7);
            RobotMap.isSpeedMode = true;
        }
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}
}