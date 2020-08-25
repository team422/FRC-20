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
    protected void execute() {
        Subsystems.helix.stopHelixMotors();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}