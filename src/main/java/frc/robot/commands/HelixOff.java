package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class HelixOff extends Command {

    public HelixOff() {
        super("HelixOff");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        Subsystems.helix.stopHelixMotors();
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