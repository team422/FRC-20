package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendClimber extends Command {
    
    public ExtendClimber() {
        super("ExtendClimber");
        requires(Subsystems.climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.climber.brakeRelease();
        Subsystems.climber.pullPins();
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