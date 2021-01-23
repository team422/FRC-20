package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/**
 * Toggles between slow and fast mode.
 */
public class SwitchGears extends CommandBase {

    public SwitchGears() {
        setName("SwitchGears");
    }

    public void execute() {
        if (RobotMap.isSpeedMode) {
            RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
            RobotMap.isSpeedMode = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(0.8, 0.7);
            RobotMap.isSpeedMode = true;
        }
    }

    public boolean isFinished() {
        return true;
    }

}