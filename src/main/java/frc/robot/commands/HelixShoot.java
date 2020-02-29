package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Turns the helix on.
 */
public class HelixShoot extends Command {

    public boolean isShot = false;
    public double numCorrect = 0;
    public double lastVelocity;

    double speed = 0.85; //might change with CTO preference

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
        if(Subsystems.flyboi.getVelocity() >= 0.789){
            Subsystems.helix.setHelixMotors(speed);
            Subsystems.helix.cellStopIn();
        } else{
            Subsystems.helix.stopHelixMotors();
        }
        if (Subsystems.flyboi.getVelocity() < 0.789){
            numCorrect++;
            if(numCorrect >= 4 && lastVelocity < Subsystems.flyboi.getVelocity()-0.05){
                isShot = true;
            }
            lastVelocity = Subsystems.flyboi.getVelocity();
        }
        else{
            numCorrect = 0;
        }
        if(isShot = true) {
            Subsystems.intake.cellCount --;
            isShot = false;
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