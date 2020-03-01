package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.RobotMap;

/**
 * Turns the helix when the flywheel is running & up to speed.
 */
public class HelixShootAuto extends Command {

    private Boolean warmedUp = false;
    private int counter = 0;

    private final double helixSpeed = 0.75;

    public HelixShootAuto() {
        super("HelixShootAuto");
        requires(Subsystems.helix);
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        warmedUp = false;
    }

    @Override
    public void execute() {
        System.out.println(Subsystems.flyboi.getPower());
        if(Subsystems.flyboi.getPower() >= 0.815) {
            if (!RobotMap.isCellStopUp) {
                Subsystems.helix.cellStopIn();
                RobotMap.isCellStopUp = true;
            } else if (counter < 5) {
                counter++;
            } else {
                Subsystems.helix.setHelixMotors(helixSpeed);
                warmedUp = true;
            }
        } else {
            Subsystems.helix.stopHelixMotors();
            if (warmedUp && Subsystems.flyboi.getPower() < 0.800) {
                warmedUp = false;
                Subsystems.intake.cellCount--;
                System.out.println("BALL SHOT, " + Subsystems.intake.cellCount + " BALLS REMAINING");
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}