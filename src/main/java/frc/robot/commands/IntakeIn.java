package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.Direction;

public class IntakeIn extends Command {

    public IntakeIn() {
        super("IntakeIn");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Subsystems.intake.intake(0.314, Direction.Up);
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