package frc.robot;

/**
 * Ports for motor controllers, caps for speed and rotation, booleans for toggles, and turn direction.
 */
public class RobotMap {

    public static double speedCap = 0.8;
    public static double rotationCap = 0.7;

    // Robot state booleans (changed throughout the match)

    public static boolean isSpeedMode = true;
    public static boolean isFirstCamera = true;
    public static boolean isIntakeDown = false;
    public static boolean isCellStopUp = false;

    // Sensor ports

    public static final int intakeBeamBreak = 9;

    // UI Ports

    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;

    // Motor, etc ports (hardcoded for comp)

    public static int leftFrontFollower = 7;
    public static int leftMiddleMaster = 29;
    public static int leftRearFollower = 8;
    public static int rightFrontFollower = 9; 
    public static int rightMiddleMaster = 58;
    public static int rightRearFollower = 45;
    public static int wheelDiameter = 6;

    public static int leftFlywheel = 2;
    public static int rightFlywheel = 1;
    public static int helicase = 32;
    public static int intakeMotor = 12;
    public static int cellStopIn = 7;
    public static int cellStopOut = 0;
    public static int intakeExtensionIn = 6;
    public static int intakeExtensionOut = 1;

    /**
     * @return The speed cap for the drive base in teleop.
     */
    public static double getSpeedCap() {
        return speedCap;
    }

    /**
     * @return The rotation speed cap for the drive base in teleop.
     */
    public static double getRotationCap() {
        return rotationCap;
    }

    /**
     * Sets the caps on speed & rotation for the drive base in teleop.
     * @param newSpeedCap The speed cap to set.
     * @param newRotationCap The rotation speed cap to set.
     */
    public static void setSpeedAndRotationCaps(final double newSpeedCap, final double newRotationCap) {
        speedCap = newSpeedCap;
        rotationCap = newRotationCap;
    }

}