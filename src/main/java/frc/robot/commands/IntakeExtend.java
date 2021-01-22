package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

/**
 * Extends the intake.
 */
public class IntakeExtend extends CommandBase {

    public IntakeExtend() {
        setName("IntakeExtend");
        addRequirements(Subsystems.intake);
    }

    public void execute() {
        Subsystems.intake.intakeExtend();
        RobotMap.isIntakeDown = true;
    }

    public boolean isFinished() {
        return true;
    }

}