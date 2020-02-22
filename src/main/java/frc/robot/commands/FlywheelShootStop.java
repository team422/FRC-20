package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class FlywheelShootStop extends CommandGroup {
    public void flyWheelShootStop () {
        addSequential(new CellStopExtend());
        addSequential(new StartStopFlywheel());
    }
}