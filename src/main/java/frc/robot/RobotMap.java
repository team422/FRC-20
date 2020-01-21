package frc.robot;

public class RobotMap {

    public static String botName = "Practice Bot";
    public static double idealAngle = 0;
    public static double driveOffset = 0;
    public static double turnDirection = 0;
    public static double speedCap = 0.25;
    public static double rotationCap = 0.5;
    
    /**
     * Various Ports
     */
    
    // Talon/Victor IDs
    //VALUES FOR TOASTER
    // public static final int leftFrontFollower = 3;
    // public static final int leftMiddleMaster = 2;
    // public static final int leftRearFollower = 6;
    // public static final int rightFrontFollower = 5;
    // public static final int rightMiddleMaster = 14;
    // public static final int rightRearFollower = 4;

    //PORTS FOR PRACTIVE BOT
    public static final int rightRearFollower = 1;
    public static final int rightMiddleMaster = 26; //switched with 20
    public static final int rightFrontFollower = 20; //switched with 26
    public static final int leftRearFollower = 35;
    public static final int leftMiddleMaster = 34;
    public static final int leftFrontFollower = 31;
    
    
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