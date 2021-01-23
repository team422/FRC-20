package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/**
 * Toggles between the intake extended and the intake retracted.
 */
public class IntakeExtendRetract extends CommandBase {

    public IntakeExtendRetract() {
        setName("IntakeExtendRetract");
        addRequirements(Subsystems.intake);
    }

    public void execute() {
        if (RobotMap.isIntakeDown) {
            Subsystems.intake.intakeRetract();
			RobotMap.isIntakeDown = false;
        } else {
            Subsystems.intake.intakeExtend();
            RobotMap.isIntakeDown = true;
        }
    }

    public boolean isFinished() {
        return true;
    }

}