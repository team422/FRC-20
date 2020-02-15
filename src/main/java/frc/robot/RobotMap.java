package frc.robot;

/**
 * Ports for motor controllers, caps for speed and rotation, and turn direction.
 */
public class RobotMap {

    public static double speedCap = 0.25;
    public static double rotationCap = 0.5;
    public static boolean isSpoodMode = false;
    public static double wheelDiameter;
    public static boolean isIntakeToggled = false;
    public static boolean isShooterToggled = false;

    // Drive base ports

    public static int leftFrontFollower;
    public static int leftMiddleMaster;
    public static int leftRearFollower;
    public static int rightFrontFollower;
    public static int rightMiddleMaster;
    public static int rightRearFollower;

    // Mechanism ports

    public static int leftFlywheel;
    public static int rightFlywheel;
    public static int helicase;
    public static int intakeMotor;
    public static int climberWinch;

    // Solenoid ports

    public static int cellStopOut;
    public static int cellStopIn;
    public static int intakeExtensionOut;
    public static int intakeExtensionIn;

    // Port switcher 

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
            leftFlywheel = 422;
            rightFlywheel = 422;
            helicase = 422;
            intakeMotor = 422;
            cellStopIn = 422;
            cellStopOut = 422;
            intakeExtensionIn = 422;
            intakeExtensionOut = 422;
        } else if (bot == BotNames.PRACTICE) {
            leftFrontFollower = 1;
            leftMiddleMaster = 20;
            leftRearFollower = 26;
            rightFrontFollower = 30;
            rightMiddleMaster = 31;
            rightRearFollower = 34;
            wheelDiameter = 6;
            leftFlywheel = 3;
            rightFlywheel = 4;
            helicase = 23;
            intakeMotor = 33;
            cellStopIn = 0;
            cellStopOut = 7;
            intakeExtensionIn = 1;
            intakeExtensionOut = 6;
        } else if (bot == BotNames.COMPETITION) {
            leftFrontFollower = 7;
            leftMiddleMaster = 29;
            leftRearFollower = 8;
            rightFrontFollower = 9;
            rightMiddleMaster = 10;
            rightRearFollower = 45;
            wheelDiameter = 6;
            leftFlywheel = 422;
            rightFlywheel = 422;
            helicase = 422;
            intakeMotor = 422;
            cellStopIn = 422;
            cellStopOut = 422;
            intakeExtensionIn = 422;
            intakeExtensionOut = 422;
        }
        }


    // Double solenoid ports

    public static final int climberBrakeIn = 422;
    public static final int climberBrakeOut = 422;
    public static final int leftClimbPinIn = 422;
    public static final int leftClimbPinOut = 422;
    public static final int rightClimbPinIn = 422;
    public static final int rightClimbPinOut = 422;

    // Sensor ports

    public static final int intakeBeamBreak = 422;

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