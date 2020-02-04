package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class RetractIntake extends Command {

    public RetractIntake() {
        super("RetractIntake");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.intake.intakeRetract();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}