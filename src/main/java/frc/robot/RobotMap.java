package frc.robot;

/**
 * Ports for motor controllers, caps for speed and rotation, booleans for toggles, and turn direction.
 */
public class RobotMap {

    // Robot settings & toggles (mutable)

    private static double speedCap = 0.8;
    private static double rotationCap = 0.7;

    public static boolean isSpeedMode = true;
    public static boolean isFirstCamera = true;
    public static boolean isIntakeDown = false;

    // Drive base ports

    public static int leftFrontFollower;
    public static int leftMiddleMaster;
    public static int leftRearFollower;
    public static int rightFrontFollower;
    public static int rightMiddleMaster;
    public static int rightRearFollower;

    public static int wheelDiameter;

    // Subsystem motor ports

    public static int leftFlywheel;
    public static int rightFlywheel;
    public static int helicase;
    public static int intakeMotor;

    // Double solenoid ports

    public static int intakeExtensionOut;
    public static int intakeExtensionIn;

    // Sensor ports

    public static final int intakeBeamBreak = 9;

    // UI Ports

    public static final int leftJoystix = 0;
    public static final int rightJoystix = 1;
    public static final int operatorXboxController = 2;


    public enum BotNames {
        COMPETITION, PRACTICE, TOASTER
    }

    public static BotNames botName;

    /**
     * Sets the bot's ports based off of the bot's name. No further robot-specific setting is needed.
     * @param bot The name of the bot.
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

            //doesn't physically exist; unused values
            leftFlywheel = 422;
            rightFlywheel = 422;
            helicase = 422;
            intakeMotor = 422;
            intakeExtensionIn = 3;
            intakeExtensionOut = 4;
        } else if (bot == BotNames.PRACTICE) {
            leftFrontFollower = 3;
            leftMiddleMaster = 34;
            leftRearFollower = 6;
            rightFrontFollower = 5;
            rightMiddleMaster = 31;
            rightRearFollower = 4;
            wheelDiameter = 6;

            leftFlywheel = 1;
            rightFlywheel = 2;
            helicase = 33;
            intakeMotor = 23;
            intakeExtensionIn = 0;
            intakeExtensionOut = 7;
        } else if (bot == BotNames.COMPETITION) {
            leftFrontFollower = 7;
            leftMiddleMaster = 29;
            leftRearFollower = 8;
            rightFrontFollower = 9;
            rightMiddleMaster = 58;
            rightRearFollower = 45;
            wheelDiameter = 6;

            leftFlywheel = 1;
            rightFlywheel = 2;
            helicase = 32;
            intakeMotor = 12;
            intakeExtensionIn = 1;
            intakeExtensionOut = 6;
        }
    }


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
     * @param newSpeedCap The speed cap to set (0 to 1).
     * @param newRotationCap The rotation speed cap to set (0 to 1).
     */
    public static void setSpeedAndRotationCaps(double newSpeedCap, double newRotationCap) {
        speedCap = (newSpeedCap > 1) ? 1 : newSpeedCap;
        rotationCap = (newRotationCap > 1) ? 1 : newRotationCap;
    }

}