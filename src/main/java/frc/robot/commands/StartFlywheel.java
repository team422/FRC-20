package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Runs the flywheel.
 */
public class StartFlywheel extends CommandBase {

    private double speed;

    public StartFlywheel() {
        setName("StartFlywheel");
        addRequirements(Subsystems.flyboi);
        this.speed = Subsystems.flyboi.wheelSpeed;
    }

    public StartFlywheel(double Speed) {
        setName("StartFlywheel");
        addRequirements(Subsystems.flyboi);
        this.speed = Speed;
    }

    public void execute() {
        Subsystems.flyboi.setShootVoltage(speed);
        System.out.println("Flywheel on");
    }

    public boolean isFinished() {
        return true;
    }

}
