package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootStop extends CommandGroup {
    public ShootStop () {
        addSequential(new CellStopExtend());
        addSequential(new StartStopFlywheel());
    }
}