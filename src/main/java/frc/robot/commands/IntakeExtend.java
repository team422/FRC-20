package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * Extends the intake.
 */
public class IntakeExtend extends Command {

    public IntakeExtend() {
        super("IntakeExtend");
        requires(Subsystems.intake);
    }

    @Override
    protected void execute() {
        Subsystems.intake.intakeExtend();
        RobotMap.isIntakeDown = true;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}