package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * Stops the intake wheels.
 */
public class IntakeOff extends Command {

    public IntakeOff() {
        super("IntakeOff");
        requires(Subsystems.intake);
    }

    @Override
    protected void execute() {
        Subsystems.intake.stopIntakeMotors();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}