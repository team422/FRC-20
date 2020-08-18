package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * Toggles between the intake extended and the intake retracted.
 */
public class IntakeExtendRetract extends Command {

    public IntakeExtendRetract() {
        super("IntakeExtendRetract");
        requires(Subsystems.intake);
    }

    @Override
    protected void execute() {
        if (RobotMap.isIntakeDown) {
            Subsystems.intake.intakeRetract();
			RobotMap.isIntakeDown = false;
        } else {
            Subsystems.intake.intakeExtend();
            RobotMap.isIntakeDown = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}