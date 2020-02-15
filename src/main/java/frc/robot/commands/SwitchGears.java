package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class SwitchGears extends Command {

    public SwitchGears() {
        super("SwitchGears");
    }

    @Override 
    public void initialize() {
        if (RobotMap.isSpoodMode) {
            RobotMap.setSpeedAndRotationCaps(0.3, 0.2);
            RobotMap.isSpoodMode = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(1, 0.35);
            RobotMap.isSpoodMode = true;
        }
    }

    @Override
    public void execute() {
    }

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