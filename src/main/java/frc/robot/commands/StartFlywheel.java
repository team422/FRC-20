package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the flywheel.
 */
public class StartFlywheel extends Command {

    private double speed;

    public StartFlywheel() {
        super("StartFlywheel");
        requires(Subsystems.flyboi);
        this.speed = Subsystems.flyboi.wheelSpeed;
    }

    public StartFlywheel(double Speed) {
        super("StartFlywheel");
        requires(Subsystems.flyboi);
        this.speed = Speed;
    }

    @Override
    protected void execute() {
        Subsystems.flyboi.setShootVoltage(speed);
        System.out.println("Flywheel on");
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
