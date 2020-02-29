package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class IsBallShot extends Command {

    public boolean isShot = false;
    public double numCorrect = 0;
    public double lastVelocity;
    
    public IsBallShot() {
        super("IsBallShot");
        requires(Subsystems.flyboi);
    }

    @Override
    public void initialize() {
        numCorrect = 0;
    }

    @Override
    public void execute() {
        
        if (Subsystems.flyboi.getVelocity() < 0.789){
            numCorrect++;
            if(numCorrect >= 5 && lastVelocity < Subsystems.flyboi.getVelocity()-0.2){
                isShot = true;
            }
            lastVelocity = Subsystems.flyboi.getVelocity();
        }
        else{
            numCorrect = 0;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}