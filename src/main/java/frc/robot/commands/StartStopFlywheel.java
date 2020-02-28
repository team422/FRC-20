package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * Toggles between the flywheel running and the flywheel stopped.
 */
public class StartStopFlywheel extends Command {

    private final double wheelSpeed = 0.77;

    public StartStopFlywheel() {
        super("StartStopFlywheel");
        requires(Subsystems.flyboi);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (RobotMap.isShooterOn) {
            Subsystems.flyboi.stopWheel();
            RobotMap.isShooterOn = false;
        } else {
            // Subsystems.flyboi.spinWheel(wheelSpeed);
            Subsystems.flyboi.setShootWithPID(wheelSpeed, -wheelSpeed);
            RobotMap.isShooterOn = true;
        }
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
