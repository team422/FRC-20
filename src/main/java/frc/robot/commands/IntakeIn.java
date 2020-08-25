package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * Spins the intake wheels in to absorb lemonade cargo.
 */
public class IntakeIn extends Command {

    public IntakeIn() {
        super("IntakeIn");
        requires(Subsystems.intake);
    }

    @Override
    protected void execute() {
        Subsystems.intake.setIntakeMotors(0.85);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}