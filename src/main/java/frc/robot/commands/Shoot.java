package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Shoot extends CommandGroup {

    public Shoot() {
        addSequential(new StartStopFlywheel());
        addSequential(new WaitCommand(1.5));
        addSequential(new RetractCellStop());
        addSequential(new TurnHelix(0.3), 4);
        addSequential(new StartStopFlywheel());
        //reset counter?
    }

}