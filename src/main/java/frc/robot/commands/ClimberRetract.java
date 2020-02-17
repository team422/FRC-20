package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

public class ClimberRetract extends Command {
    
    public ClimberRetract() {
        super("ClimberRetract");
        requires(Subsystems.climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.climber.contractClimber(0.3);
        Subsystems.climber.stopClimber();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
        Subsystems.climber.brakeDown();
    }

    @Override
    public void end() {
        Subsystems.climber.brakeDown();
    }
}