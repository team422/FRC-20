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
    }

    @Override
    public void initialize() {
        Subsystems.helix.setHelixMotors(-0.8);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {
        Subsystems.helix.stopHelixMotors();
    }

    @Override
    public void end() {
        Subsystems.helix.stopHelixMotors();
    }

}