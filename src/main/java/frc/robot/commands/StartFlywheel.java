package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.*;

/**
 * A Command to start the flywheel.
 */
public class StartFlywheel extends Command {

    public StartFlywheel() {
        super ("StartFlywheel");
        requires(Subsystems.flyboi);
        requires(Subsystems.helix);
    }

    protected void initialize() {}

    protected void execute() {
        Subsystems.flyboi.spinWheel(0.55);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void interrupted() {}

    protected void end() {}
}