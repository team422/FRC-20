package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand; 

/**
 * Warms up the flywheel, then starts shooting out power cells.
 */
public class Shoot extends CommandGroup {
    public Shoot() {
        addSequential(new StartStopFlywheel());
        // addSequential(new WaitCommand(0.7));
        // addSequential(new CellStopRetract());
        addSequential(new HelixShoot());
    }
}