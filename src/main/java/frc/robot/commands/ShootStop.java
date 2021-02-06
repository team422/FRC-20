package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Returns the shooter and helix to their resting positions/stops Shoot.
 */
public class ShootStop extends SequentialCommandGroup {
    public ShootStop () {
        addCommands(new HelixOff());
        addCommands(new StopFlywheel());
    }
}