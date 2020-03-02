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
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.intake.stopIntakeMotors();
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