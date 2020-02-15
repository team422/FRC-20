package frc.robot;

/**
 * Ports for motor controllers, caps for speed and rotation, and turn direction.
 */
public class RobotMap {

    public static double speedCap = 0.25;
    public static double rotationCap = 0.5;
    public static double wheelDiameter;

    // Drive base ports

    public static int leftFrontFollower;
    public static int leftMiddleMaster;
    public static int leftRearFollower;
    public static int rightFrontFollower;
    public static int rightMiddleMaster;
    public static int rightRearFollower;

    public enum BotNames {
        COMPETITION, PRACTICE, TOASTER
    }

    /**
     * <p><code>"toaster"</code>, <code>"practice"</code>, or <code>"comp"</code>.</p>
     * Please note that this is case sensitive.
     */
    public static BotNames botName = BotNames.COMPETITION;

    /**
     * Sets the bot's ports based off of the bot's name. You will still have to comment out code in DriveBase.java that determines whether your motors are victors or talons.
     * @param bot The name of the bot. <code>"toaster"</code>, <code>"practice"</code>, or <code>"comp"</code>.
     */
    public static void setBot(BotNames bot) {
        botName = bot;
        if (bot == BotNames.TOASTER) {
            leftFrontFollower = 43;
            leftMiddleMaster = 18;
            leftRearFollower = 2;
            rightFrontFollower = 60;
            rightMiddleMaster = 14;
            rightRearFollower = 57;

            wheelDiameter = 8;
        } else if (bot == BotNames.PRACTICE) {
            leftFrontFollower = 3;
            leftMiddleMaster = 20;
            leftRearFollower = 6;
            rightFrontFollower = 5;
            rightMiddleMaster = 31;
            rightRearFollower = 4;

            wheelDiameter = 6;
        } else if (bot == BotNames.COMPETITION) {
            leftFrontFollower = 7;
            leftMiddleMaster = 29;
            leftRearFollower = 8;
            rightFrontFollower = 9;
            rightMiddleMaster = 10;
            rightRearFollower = 45;

            wheelDiameter = 6;
        }
    }

    // Subsystem motor controller ports (Talons/Victors)

    public static final int leftFlywheel = 11;
    public static final int rightFlywheel = 24;
    public static final int intake = 2;

    // Double solenoid ports

    public static final int intakeExtensionOut = 4;
    public static final int intakeExtensionIn = 2;

    // UI Ports

    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;

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