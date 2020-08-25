package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the flywheel.
 */
public class StopFlywheel extends Command {

    public StopFlywheel() {
        super("StopFlywheel");
        requires(Subsystems.flyboi);
    }

    @Override
    protected void execute() {
        Subsystems.flyboi.stopWheel();
        System.out.println("Flywheel off");
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
