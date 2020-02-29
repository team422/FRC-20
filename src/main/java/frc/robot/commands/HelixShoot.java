package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import frc.robot.RobotMap;

/**
 * Turns the helix on.
 */
public class HelixShoot extends Command {

    private Boolean warmedUp = false;
    private int counter = 0;

    private final double helixSpeed = 0.75;
    private final double shootSpeed = 0.815;

    public HelixShoot() {
        super("HelixShoot");
        requires(Subsystems.helix);
        requires(Subsystems.flyboi);
    }

    @Override
    public void initialize() {
        warmedUp = false;
    }

    @Override
    public void execute() {
        System.out.println(Subsystems.flyboi.getPower());
        if(Subsystems.flyboi.getPower() >= 0.796) {
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
            if (warmedUp && Subsystems.flyboi.getPower() < 0.776) {
                warmedUp = false;
                Subsystems.intake.cellCount--;
            }
        }

    }

    @Override
    public boolean isFinished() {
        return UserInterface.operatorController.getRightTrigger() < 0.4;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}

}