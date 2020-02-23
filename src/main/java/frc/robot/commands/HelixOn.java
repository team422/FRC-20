package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class HelixOn extends Command {

    double speed = 0.8;

    public HelixOn() {
        super("HelixOn");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        Subsystems.helix.setHelixMotors(speed);
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}