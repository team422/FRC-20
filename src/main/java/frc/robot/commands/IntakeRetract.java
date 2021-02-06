package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/**
 * Retracts the intake.
 */
public class IntakeRetract extends CommandBase {

    public IntakeRetract() {
        setName("IntakeRetract");
        addRequirements(Subsystems.intake);
    }

    public void execute() {
        Subsystems.intake.intakeRetract();
        RobotMap.isIntakeDown = false;
    }

    public boolean isFinished() {
        return true;
    }

}