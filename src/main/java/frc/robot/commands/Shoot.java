package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Warms up the flywheel, then starts shooting out power cells.
 */
public class Shoot extends SequentialCommandGroup {
    public Shoot() {
        addCommands(new HelixTurn().withTimeout(0.3));
        addCommands(new StartFlywheel());
        addCommands(new HelixShoot());
    }
}