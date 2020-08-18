package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.ShuffleboardControl;

public class SetAuto extends Command {

    public SetAuto() {
        super("Select");
    }

    @Override
    protected void execute() {
        ShuffleboardControl.setAutonomous();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
}