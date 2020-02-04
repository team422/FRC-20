package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.*;

/**
 * A Command to stop the flywheel.
 */
public class StopFlywheel extends Command {

    public StopFlywheel() {
        super("StopFlywheel");
        requires(Subsystems.flyboi);
        requires(Subsystems.helix);
    }

    protected void initialize() {}

    protected void execute() {
        Subsystems.flyboi.stopWheel();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void interrupted() {}

    protected void end() {}
}