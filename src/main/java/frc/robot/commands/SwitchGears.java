package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * A command to toggle between slow and fast mode. 
 */
public class SwitchGears extends Command {

    public SwitchGears() {
        super("SwitchGears");
    }

    @Override
    public void initialize() {
        if (RobotMap.isFastMode) {
            RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
            RobotMap.isFastMode = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(0.8, 0.7);
            RobotMap.isFastMode = true;
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