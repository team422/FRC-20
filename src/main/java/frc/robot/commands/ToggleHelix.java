package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * A command to toggle between the intake extended and the intake retracted.
 */
public class ToggleHelix extends Command {

    public ToggleHelix() {
        super("ToggleHelix");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        if (RobotMap.isHelixToggled) {
            Subsystems.helix.stopHelixMotors();
			RobotMap.isHelixToggled = false;
        } else {
            Subsystems.helix.setHelixMotors(0.8);
            RobotMap.isHelixToggled = true;
        }
    }

    @Override
    public void execute() {}

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