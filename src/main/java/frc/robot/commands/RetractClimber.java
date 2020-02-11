package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

public class RetractClimber extends Command {
    
    public RetractClimber() {
        super("RetractClimber");
        requires(Subsystems.climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.climber.contractClimber();
        Subsystems.climber.stopClimber();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
        Subsystems.climber.brakeSet();
    }

    @Override
    public void end() {
        Subsystems.climber.brakeSet();
    }
}