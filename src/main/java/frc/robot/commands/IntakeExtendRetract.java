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
    public void initialize() {}

    @Override
    public void execute() {
        if (RobotMap.isIntakeDown) {
            Subsystems.intake.intakeRetract();
			RobotMap.isIntakeDown = false;
        } else {
            Subsystems.intake.intakeExtend();
            RobotMap.isIntakeDown = true;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
    }

    @Override
    public void end() {
    }
}