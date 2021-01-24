package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

/**
 * Spins the intake wheels in to absorb lemonade cargo.
 */
public class IntakeIn extends CommandBase {

    public IntakeIn() {
        setName("IntakeIn");
        addRequirements(Subsystems.intake);
    }

    public void execute() {
        Subsystems.intake.setIntakeMotors(0.85);
    }

    public boolean isFinished() {
        return true;
    }

}