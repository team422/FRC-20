package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Turns the helix when the flywheel is running & up to speed. If this is executed in auto, it must have a timeout.
 */
public class HelixShoot extends CommandBase {

    private boolean warmedUp = false;
    private int counter = 0;

    private final double helixSpeed = 0.60;

    public HelixShoot() {
        setName("HelixShoot");
        addRequirements(Subsystems.helix);
    }

    public void initialize() {
        warmedUp = false;
    }

    public void execute() {
        System.out.println(Subsystems.flyboi.getPower());
        if(Subsystems.flyboi.getPower() >= Subsystems.flyboi.wheelSpeed-0.070) {
            if (counter < 6) {
                counter++;
            } else {
                Subsystems.helix.setHelixMotors(helixSpeed);
                warmedUp = true;
            }
        } else {
            Subsystems.helix.stopHelixMotors();
            if (warmedUp && Subsystems.flyboi.getPower() < Subsystems.flyboi.wheelSpeed-0.020) {
                warmedUp = false;
                Subsystems.helix.cellCount--;
                System.out.println("BALL SHOT, " + Subsystems.helix.cellCount + " BALLS REMAINING");
            }
        }
    }

    public boolean isFinished() {
        if (DriverStation.getInstance().isAutonomous()) {
            return false;
        } else {
            return UserInterface.operatorController.getRightTrigger() < 0.4;
        }
    }
}