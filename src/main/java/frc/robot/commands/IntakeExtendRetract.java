package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * A command to toggle between the intake extended and the intake retracted.
 */
public class IntakeExtendRetract extends Command {

    public IntakeExtendRetract() {
        super("IntakeExtendRetract");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {
        if (RobotMap.isIntakeDown) {
            Subsystems.intake.intakeExtend();
			RobotMap.isIntakeDown = false;
        } else {
            Subsystems.intake.intakeRetract();
            RobotMap.isIntakeDown = true;
        }
    }

    @Override
    public void execute() {}

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