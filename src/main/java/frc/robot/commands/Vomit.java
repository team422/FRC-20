package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

/**
 * Spins the flywheel, helix, and intake motors all backwards in order to expel power cells.
 */
public class Vomit extends CommandBase {

    public Vomit() {
        setName("Vomit");
        addRequirements(Subsystems.flyboi, Subsystems.helix);
        //still has control over intake up/down
    }

    public void initialize() {
        Subsystems.flyboi.spinWheel(-0.6);
        Subsystems.helix.setHelixMotors(-0.9);
        Subsystems.intake.setIntakeMotors(-0.8);
    }

    public void end(boolean interrupted) {
        Subsystems.flyboi.stopWheel();
        Subsystems.helix.stopHelixMotors();
        Subsystems.intake.stopIntakeMotors();
    }

}