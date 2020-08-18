package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * Turns the helix on.
 */
public class HelixTurn extends Command {

    public HelixTurn() {
        super("HelixTurn");
        requires(Subsystems.helix);
        setTimeout(300);
    }

    public HelixTurn(double Timeout) {
        super("HelixTurn");
        requires(Subsystems.helix);
        setTimeout(Timeout);
    }

    @Override
    protected void initialize() {
        Subsystems.helix.setHelixMotors(-0.8);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void interrupted() {
        Subsystems.helix.stopHelixMotors();
    }

    @Override
    protected void end() {
        Subsystems.helix.stopHelixMotors();
    }

}