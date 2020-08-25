package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * Retracts the intake.
 */
public class IntakeRetract extends Command {

    public IntakeRetract() {
        super("IntakeRetract");
        requires(Subsystems.intake);
    }

    @Override
    protected void execute() {
        Subsystems.intake.intakeRetract();
        RobotMap.isIntakeDown = false;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}