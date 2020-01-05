package frc.robot;

public class RobotMap {
    /**
     * Whether or not this is the competition bot. Changing this variable
     * will change all ports accordingly. MAKE SURE YOU GO THROUGH DRIVEBASE
     * AND CARGO SUBSYSTEMS AND CHANGE VICTOR/TALON CLASSES ACCORDINGLY.
     */
    public static final boolean isCompBot = true;
    
    /**
     * Sets which joystick of the driverXboxController that
     * the throttle for the drivebase is controlled by.
     * DEFAULT IS RIGHT.
     */
    public static final boolean isLeftThrottle = false;
    
    /**
     * Sets additional settings/booleans.
     */
    public static double idealAngle = 0;
    public static double driveOffset = 0;
    public static double turnDirection = 0;
    public static boolean isCamera1 = true;
    public static boolean isFastMode = false;
    public static double speedCap = 0.2;
    public static double rotationCap = 0.2;
    public static String botName = (isCompBot) ? "Meridian" : "Hot Take";
    public static boolean isHoldingPivotUp = false;
    public static boolean cargoIsIn = false;
    public static boolean flapIsUp = true;
    public static boolean armIsOut = false;
    public static boolean highPivotCurrent = false;
    
    /**
     * Various Ports
     */
    
    // Talon/Victor IDs
    public static final int leftFrontFollower = (isCompBot) ? 4 : 20;
    public static final int leftMiddleMaster = (isCompBot) ? 11 : 21;
    public static final int leftRearFollower = (isCompBot) ? 6 : 22;
    public static final int rightFrontFollower = (isCompBot) ? 3 : 35;
    public static final int rightMiddleMaster = (isCompBot) ? 12 : 34;
    public static final int rightRearFollower = (isCompBot) ? 5 : 33;
    // public static final int leftFrontClimb = (isCompBot) ? 1 : 21;
    // public static final int leftBackClimb = (isCompBot) ? 7 : 26;
    // public static final int rightFrontClimb = (isCompBot) ? 2 : 25;
    // public static final int rightBackClimb = (isCompBot) ? 14 : 24;
    public static final int cargoEscalatorWheels = (isCompBot) ? 9 : 24;
    public static final int cargoIntakeWheels = (isCompBot) ? 8 : 25;
    public static final int cargoIntakePivot = (isCompBot) ? 13 : 23;
    public static final int extraMotorController = (isCompBot) ? 10 : 26;
    
    // Double Solenoid Values (PCM 0)
    public static final int cargoFlapUp = (isCompBot) ? 5 : 6;
    public static final int cargoFlapDown = (isCompBot) ? 2 : 7;
    public static final int hatchArmOut = (isCompBot) ? 4 : 5;
    public static final int hatchArmIn = (isCompBot) ? 3 : 2;
    public static final int hatchClamp = (isCompBot) ? 6 : 1;
    public static final int hatchRelease = (isCompBot) ? 1 : 0;

    // Double Solenoid Values (PCM 1)
    public static final int closeClimberExtend = 4;
    public static final int closeClimberRetract = 3;
    public static final int farClimberExtend = 5;
    public static final int farClimberRetract = 2;


    // Digital IO Ports
    public static final int cargoPivotUltrasonic = 8;
    public static final int cargoEscalatorUltrasonic = 7;
    public static final int cargoIntakeUltrasonic = 9;
    public static final int climberUltrasonic = 3;
    
    // UI Ports
    public static final int launchpad = 0;
    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;
    /**
     * End Port Setting
     */
   
    public static double getIdealAngle() {
        return idealAngle;
    }
    
    public static void setIdealAngle(double angle) {
        idealAngle = angle;
        System.out.println("Ideal angle is now " + idealAngle);
    }
    
    public static double getSpeedCap() {
        return speedCap;
    }
    
    public static double getRotationCap() {
        return rotationCap;
    }
    
    public static void setSpeedAndRotationCaps(double newSpeedCap, double newRotationCap) {
        speedCap = newSpeedCap;
        rotationCap = newRotationCap;
    }
    
    public static void setDriveOffset(double offset) {
        driveOffset = offset;
        System.out.println("Drive offset is now " + driveOffset);
    }
    
    public static double getDriveOffset() {
        return driveOffset;
    }
    
    public static double getTurnDirection() {
        return turnDirection;
    }
    
    public static void setTurnDirection(double direction) {
        turnDirection = direction;
    }
    
    public static void setCamera(boolean camera) {
        isCamera1 = camera;
    }
   
    public static boolean getCamera() {
        return isCamera1;
    }
}