package frc.robot.userinterface;

import frc.robot.RobotMap;

/**
 * Contains instances of all UI elements.
 */
public class UserInterface {

    /**
     * <p>The operator controller (black).</p>
     * Used to control all subsystems except the drive base in teleop.
     */

    public static final Joystix leftJoystix = new Joystix(RobotMap.leftJoystix);
    public static final Joystix rightJoystix = new Joystix(RobotMap.rightJoystix);
    public static final RumbleXboxController operatorController = new RumbleXboxController(RobotMap.operatorXboxController);

}