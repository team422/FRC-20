package frc.robot;

/**
 * Ports for motor controllers, caps for speed and rotation, and turn direction.
 */
public class RobotMap {

    public static double speedCap = 0.8;
    public static double rotationCap = 0.7;
    public static double wheelDiameter;

    public static boolean isSpeedMode = true;
    public static boolean isFirstCamera = true;
    public static boolean isIntakeDown = false;
    public static boolean isCellStopUp = false;
    // public static boolean arePinsOut = false;
    // public static boolean isClimberBrakeToggled = false;
    // public static boolean isCellyToggled = false;

    // Drive base ports

    public static int leftFrontFollower;
    public static int leftMiddleMaster;
    public static int leftRearFollower;
    public static int rightFrontFollower;
    public static int rightMiddleMaster;
    public static int rightRearFollower;

    // Subsystem motor ports

    public static int leftFlywheel;
    public static int rightFlywheel;
    public static int helicase;
    public static int intakeMotor;
    // public static int climberWinch;

    // Double solenoid ports

    public static int cellStopOut;
    public static int cellStopIn;
    public static int intakeExtensionOut;
    public static int intakeExtensionIn;
    // public static final int climberBrakeIn = 0; //!
    // public static final int climberBrakeOut = 1; //!
    // public static final int leftClimbPinIn = 2; //!
    // public static final int leftClimbPinOut = 3; //!
    // public static final int rightClimbPinIn = 4; //!
    // public static final int rightClimbPinOut = 5; //!

    // Sensor ports

    public static final int intakeBeamBreak = 9;

    // UI Ports

    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;


    public enum BotNames {
        COMPETITION, PRACTICE, TOASTER
    }

    /**
     * <p><code>"toaster"</code>, <code>"practice"</code>, or <code>"comp"</code>.</p>
     * Please note that this is case sensitive.
     */
    public static BotNames botName = BotNames.PRACTICE;

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

            //doesn't really exist, assign values
            leftFlywheel = 422;
            rightFlywheel = 422;
            helicase = 422;
            intakeMotor = 422;
            cellStopIn = 1;
            cellStopOut = 2;
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

            leftFlywheel = 1;
            rightFlywheel = 2;
            helicase = 32;
            intakeMotor = 12;
            cellStopIn = 0;
            cellStopOut = 7;
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
     * @param newSpeedCap The speed cap to set.
     * @param newRotationCap The rotation speed cap to set.
     */
    public static void setSpeedAndRotationCaps(final double newSpeedCap, final double newRotationCap) {
        speedCap = newSpeedCap;
        rotationCap = newRotationCap;
    }

}