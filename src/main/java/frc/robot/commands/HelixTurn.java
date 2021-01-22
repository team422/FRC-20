package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

/**
 * Turns the helix on.
 */
public class HelixTurn extends CommandBase {

    public HelixTurn() {
        setName("HelixTurn");
        addRequirements(Subsystems.helix);
    }

    public void initialize() {
        Subsystems.helix.setHelixMotors(-0.8);
    }

    public void end(boolean interrupted) {
        Subsystems.helix.stopHelixMotors();
    }

}