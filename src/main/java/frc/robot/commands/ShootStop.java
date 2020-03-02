package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Returns the shooter and helix to their resting positions/stops Shoot.
 */
public class ShootStop extends CommandGroup {
    public ShootStop () {
        addSequential(new HelixOff());
        addSequential(new CellStopExtend());
        addSequential(new StopFlywheel());
    }
}