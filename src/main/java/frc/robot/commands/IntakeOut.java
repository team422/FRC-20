package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class IntakeOut extends Command {

    public IntakeOut() {
        super("IntakeOut");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {
        Subsystems.intake.setIntakeMotors(-0.3);
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
    public void interrupted() {
        Subsystems.intake.setIntakeMotors(0);
    }

    @Override
    public void end() {
        Subsystems.intake.setIntakeMotors(0);
    }
}