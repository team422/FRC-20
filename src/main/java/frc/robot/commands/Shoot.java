package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Warms up the flywheel, then starts shooting out power cells.
 */
public class Shoot extends CommandGroup {
    public Shoot() {
        addSequential(new StartStopFlywheel());
        addSequential(new HelixShoot());
    }
}