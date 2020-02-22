package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

/**
 * A command to extend the intake.
 */
public class IntakeExtend extends Command {

    public IntakeExtend() {
        super("IntakeExtend");
        requires(Subsystems.intake);
    }

    @Override
    public void initialize() {
        if (RobotMap.isIntakeDown) {
            System.out.println("Going up Intake part");
            Subsystems.intake.intakeRetract();
            RobotMap.isIntakeDown = false;
            
        } 
        else {
            System.out.println("Going down Intake Part");
            Subsystems.intake.intakeExtend();
            RobotMap.isIntakeDown = true;
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