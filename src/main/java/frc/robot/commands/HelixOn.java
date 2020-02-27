package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * Turns the helix on.
 */
public class HelixOn extends Command {

    double speed = 0.5;

    public HelixOn() {
        super("HelixOn");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.helix.setHelixMotors(speed);
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