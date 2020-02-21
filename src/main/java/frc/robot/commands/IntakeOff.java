package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

/**
 * A command to stop the intake wheels.
 */
public class IntakeOff extends Command {

    public IntakeOff() {
        super("IntakeOff");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {
        Subsystems.intake.stopIntakeMotors();
    }

    @Override
    public void execute() {
        //motors still on
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}
}