package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;

/**
 * Stops the intake wheels.
 */
public class IntakeOff extends CommandBase {

    public IntakeOff() {
        setName("IntakeOff");
        addRequirements(Subsystems.intake);
    }

    public void execute() {
        Subsystems.intake.stopIntakeMotors();
    }

    public boolean isFinished() {
        return true;
    }

}