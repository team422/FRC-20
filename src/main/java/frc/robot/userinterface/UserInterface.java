package frc.robot.userinterface;

import frc.robot.RobotMap;

/**
 * Contains instances of all UI elements.
 */
public class UserInterface {

    /**
     * <p>The driver controller (white).</p>
     * Used to control the drive base alone in teleop.
     */
    public static final RumbleXboxController driverController = new RumbleXboxController(RobotMap.driverXboxController);
    /**
     * <p>The operator controller (black).</p>
     * Used to control all subsystems except the drive base in teleop.
     */
    public static final RumbleXboxController operatorController = new RumbleXboxController(RobotMap.operatorXboxController);

}