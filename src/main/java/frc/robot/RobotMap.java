package frc.robot;

public class RobotMap {
    /**
     * Whether or not this is the competition bot. Changing this variable
     * will change all ports accordingly. MAKE SURE YOU GO THROUGH DRIVEBASE
     * AND CARGO SUBSYSTEMS AND CHANGE VICTOR/TALON CLASSES ACCORDINGLY.
     */
    public static final boolean isCompBot = true;
    
    /**
     * Sets additional settings/booleans.
     */
    public static String botName = (isCompBot) ? "Comp bot" : "Practice bot";
    public static double idealAngle = 0;
    public static double driveOffset = 0;
    public static double turnDirection = 0;
    public static double speedCap = 0.2;
    public static double rotationCap = 0.2;
    
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
    
    // Double Solenoid Values (PCM 0)

    // Double Solenoid Values (PCM 1)

    // Digital IO Ports
    
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
}