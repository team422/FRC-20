package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class TurnHelix extends Command {

    double speed;

    public TurnHelix(double Speed) {
        super("TurnHelix");
        requires(Subsystems.helix);
        speed = Speed;
    }

    @Override
    public void initialize() {
        Subsystems.helix.setHelixMotors(speed);
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
        Subsystems.helix.setHelixMotors(0);
    }

    @Override
    public void end() {
        Subsystems.helix.setHelixMotors(0);
    }

}