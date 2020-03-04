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
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.intake.setIntakeMotors(0.8);
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