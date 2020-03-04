package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the flywheel.
 */
public class StartFlywheel extends Command {

    public StartFlywheel() {
        super("StartFlywheel");
        requires(Subsystems.flyboi);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.flyboi.setShootVoltage(Subsystems.flyboi.wheelSpeed);
        System.out.println("Flywheel on");
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
