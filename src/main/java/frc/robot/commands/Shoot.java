package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.*;

/**
 * A Command to shoot lemonade cargo at power port.
 */
public class Shoot extends Command {

    private int numOfBalls;

    /**
     * Shoots lemonade cargo at power port.
     * @param NumOfBalls Number of lemonade cargo to shoot.
     */
    public Shoot(int NumOfBalls) {
        super ("Shoot");
        requires(Subsystems.flyboi);
        //requires(Subsystems.helix);
        numOfBalls = NumOfBalls;
    }

    protected void initialize() {
        setTimeout(0.3*numOfBalls); //0.3 seconds per ball (change)

        Subsystems.flyboi.spinWheel(0.5);
    }

    protected void execute() {
        //motors are still on
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void interrupted() {
        Subsystems.flyboi.stopWheel();
    }

    protected void end() {
        Subsystems.flyboi.stopWheel();
    }

}