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
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.flyboi.stopWheel();
        System.out.println("Flywheel off");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}
}
