package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Turns the helix on.
 */
public class HelixShoot extends Command {

    double speed = 0.5; //might change with CTO preference

    public HelixShoot() {
        super("HelixShoot");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        Subsystems.helix.setHelixMotors(speed);
    }

    @Override
    public void execute() {
        if(Subsystems.flyboi.leftEncoder.getVelocity() >= 0.789){
            Subsystems.helix.setHelixMotors(speed);
        } else {
            Subsystems.helix.stopHelixMotors();
        }
    }

    @Override
    public boolean isFinished() {
        return !UserInterface.operatorController.RB.get();
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}