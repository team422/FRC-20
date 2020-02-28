package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * Turns the helix off.
 */
public class HelixOff extends Command {

    public HelixOff() {
        super("HelixOff");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Subsystems.helix.stopHelixMotors();
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