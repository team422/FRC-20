package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Turns the helix on.
 */
public class HelixShoot extends Command {

    public Boolean warmedUp = false;

    double speed = 0.75; //might change with CTO preference

    public HelixShoot() {
        super("HelixShoot");
        requires(Subsystems.helix);
    }

    @Override
    public void initialize() {
        warmedUp = false;
        Subsystems.helix.setHelixMotors(speed);
    }

    @Override
    public void execute() {
        if(Subsystems.flyboi.getPower() >= 0.789) {
            Subsystems.helix.cellStopIn();
            Subsystems.helix.setHelixMotors(speed);
            warmedUp = true;
        } else{
            Subsystems.helix.stopHelixMotors();
            if (warmedUp && Subsystems.flyboi.getPower() < 0.77) {
                warmedUp = false;
                Subsystems.intake.cellCount--;
            }
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