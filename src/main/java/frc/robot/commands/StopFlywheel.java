package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Stops the flywheel.
 */
public class StopFlywheel extends CommandBase {

    public StopFlywheel() {
        setName("StopFlywheel");
        addRequirements(Subsystems.flyboi);
    }

    public void execute() {
        Subsystems.flyboi.stopWheel();
        System.out.println("Flywheel off");
    }

    public boolean isFinished() {
        return true;
    }

}
