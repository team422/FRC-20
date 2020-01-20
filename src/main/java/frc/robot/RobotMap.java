package frc.robot;

public class RobotMap {

    public static double idealAngle = 0;
    public static double driveOffset = 0;
    public static double turnDirection = 0;
    public static double speedCap = 0.25;
    public static double rotationCap = 0.5;
    
    public static int leftFrontFollower;
    public static int leftMiddleMaster;
    public static int leftRearFollower;
    public static int rightFrontFollower;
    public static int rightMiddleMaster;
    public static int rightRearFollower;

    String botName = "Toaster";
    
/*     enum Bot {TOASTER, PBOT; //COMPBOT}
    Bot botName;
    public void switchBotName(){
        switch (botName){
            case TOASTER:
            leftFrontFollower = 3;
            leftMiddleMaster = 2;
            leftRearFollower = 6;
            rightFrontFollower = 5;
            rightMiddleMaster = 14;
            rightRearFollower = 4;
            break;
            case PBOT:
            rightRearFollower = 1;
            rightMiddleMaster = 20;
            rightFrontFollower = 26;
            leftRearFollower = 35;
            leftMiddleMaster = 34;
            leftFrontFollower = 31;
            break;
            /* case COMPBOT:
            break; */
    
    
    public String bot = "toaster";
    
    public void setBot(String bot) {
        if (bot == "toaster") {
            leftFrontFollower = 3;
            leftMiddleMaster = 2;
            leftRearFollower = 6;
            rightFrontFollower = 5;
            rightMiddleMaster = 14;
            rightRearFollower = 4;
        } else if (bot == "practice") {
            rightRearFollower = 1;
            rightMiddleMaster = 20;
            rightFrontFollower = 26;
            leftRearFollower = 35;
            leftMiddleMaster = 34;
            leftFrontFollower = 31;
        } //else if (bot = "compBot"){
            // compBot port numbers}
        else {
            System.out.println("Not bot, try again");
        }
    
    } 

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
    
    public static void setIdealAngle(final double angle) {
        idealAngle = angle;
        System.out.println("Ideal angle is now " + idealAngle);
    }
    
    public static double getSpeedCap() {
        return speedCap;
    }
    
    public static double getRotationCap() {
        return rotationCap;
    }
    
    public static void setSpeedAndRotationCaps(final double newSpeedCap, final double newRotationCap) {
        speedCap = newSpeedCap;
        rotationCap = newRotationCap;
    }
    
    public static void setDriveOffset(final double offset) {
        driveOffset = offset;
        System.out.println("Drive offset is now " + driveOffset);
    }
    
    public static double getDriveOffset() {
        return driveOffset;
    }
    
    public static double getTurnDirection() {
        return turnDirection;
    }
    
    public static void setTurnDirection(final double direction) {
        turnDirection = direction;
    }
}