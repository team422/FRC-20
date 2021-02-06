package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

/**
 * Turns the helix off.
 */
public class HelixOff extends CommandBase {

    public HelixOff() {
        setName("HelixOff");
        addRequirements(Subsystems.helix);
    }

    public void execute() {
        Subsystems.helix.stopHelixMotors();
    }

    public boolean isFinished() {
        return true;
    }

}