package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class IntakeExtend extends Command {

    public IntakeExtend() {
        super("IntakeExtend");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Subsystems.intake.intakeExtend();
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