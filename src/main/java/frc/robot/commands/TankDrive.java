package frc.robot.commands;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import io.github.pseudoresonance.pixy2api.*;
/**
 * Uses joystick values to drive the bot in teleop.
 */
public class TankDrive extends Command {
    private double updatedSpeed = 0;
    private double updatedRotation = 0;
    private static final double maxChange = 0.5; //maxChange is acceleration
    private int frameWidth = Subsystems.pixy.getFrameWidth();
    public TankDrive() {
        super("TankDrive");
        requires(Subsystems.driveBase);
    }
    protected void initialize() {}
    protected void execute() {
        double speed;
        double rotation;
        Pixy2CCC.Block block = Subsystems.pixy.getBiggestBlock();

        if (UserInterface.operatorController.getBumper(Hand.kLeft)) {
            
            if (block != null) {
                System.out.println(block);
                if (block.getX() > (frameWidth / 2)) {
                    Subsystems.driveBase.setMotors(0, 0.1); //consider adding speed to right motors
                } else if (block.getX() < (frameWidth / 2)) {
                    Subsystems.driveBase.setMotors(0.1, 0); //consider adding speed to left motors
                } else {
                    if (block.getWidth() < 20){
                        Subsystems.driveBase.setMotors(0.1, 0.1);
                        System.out.println("Small boi");
                    }
                }
                return;
            }
            
        }
        if (Subsystems.pixy.getBiggestBlock() != null) {
            System.out.println("Yes, block!");
            System.out.println(Subsystems.pixy.getBiggestBlock());
        } else {
            System.out.println("Nooo!");
        }
        
        /* Sets throttle for driveBase to the left stick Y-axis and sets the rotation
        * for driveBase to the right stick X-axis on on the driverXboxController */
        if (UserInterface.driverController.getLeftJoystickY() < -0.1) {
            speed = -(Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
        } else if (UserInterface.driverController.getLeftJoystickY() > 0.1) {
            speed = (Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
        } else {
            speed = 0;
        }
        updatedSpeed = speed;
        if (UserInterface.driverController.getLeftJoystickX() < -0.05) {
            rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
        } else if (UserInterface.driverController.getLeftJoystickX() > 0.05) {
            rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
        } else {
            rotation = 0;
        }
        updatedRotation = -rotation;
        double speedDifference = speed - updatedSpeed;
        if (speedDifference > maxChange) {
            speed = updatedSpeed + maxChange;
        } else if (speedDifference < -maxChange) {
            speed = updatedSpeed - maxChange;
        }
        double rotationDifference = rotation - updatedRotation;
        if (rotationDifference > maxChange) {
            rotation = updatedRotation + maxChange;
            } else if (rotationDifference < -maxChange) {
            rotation = updatedRotation - maxChange;
        }
        /** Because of a weird glitch with how curvatureDrive is set up,
         *  the rotation actually goes in as the first input, followed by the speed,
         *  rather than speed then rotation */
            Subsystems.driveBase.cheesyDrive.curvatureDrive(RobotMap.getRotationCap() * rotation, RobotMap.getSpeedCap() * speed, true);
    }
    protected boolean isFinished() {
        return false;
    }
    protected void interrupted() {}
    protected void end() {}
}



