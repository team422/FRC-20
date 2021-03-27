package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Uses joystick values to drive the bot in teleop.
 */
public class TankDrive extends CommandBase {

    private double updatedLeftSpeed = 0;
    private double updatedRightSpeed = 0;
    private static final double maxChange = 0.5; //maxChange is acceleration

    public TankDrive() {
        setName("TankDrive");
        addRequirements(Subsystems.driveBase);
    }

    public void execute() {
        double leftSpeed, rightSpeed;

        /* Sets throttle for driveBase to the left stick Y-axis and sets the rotation
        * for driveBase to the right stick X-axis on on the driverXboxController */
        if (UserInterface.driverController.getLeftJoystickY() < -0.1) {
            leftSpeed = -(Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
        } else if (UserInterface.driverController.getLeftJoystickY() > 0.1) {
            leftSpeed = (Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
        } else {
            leftSpeed = 0;
        }

        if (UserInterface.driverController.getRightJoystickY() < -0.1) {
            rightSpeed = -(Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
        } else if (UserInterface.driverController.getRightJoystickY() > 0.1) {
            rightSpeed = (Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
        } else {
            rightSpeed = 0;
        }

        double leftSpeedDifference = leftSpeed - updatedLeftSpeed;
        if (leftSpeedDifference > maxChange) {
            leftSpeed = updatedLeftSpeed + maxChange;
        } else if (leftSpeedDifference < -maxChange) {
            leftSpeed = updatedLeftSpeed - maxChange;
        }
        
        double rightSpeedDifference = rightSpeed - updatedRightSpeed;
        if (rightSpeedDifference > maxChange) {
            rightSpeed = updatedRightSpeed + maxChange;
        } else if (rightSpeedDifference < -maxChange) {
            rightSpeed = updatedRightSpeed - maxChange;
        }

        updatedLeftSpeed = leftSpeed;
        updatedRightSpeed = rightSpeed;

        System.out.println("right speed is " + rightSpeed + "left speed is " + leftSpeed);
        if (rightSpeed * leftSpeed > 0){ //turn
            Subsystems.driveBase.setMotors(leftSpeed * RobotMap.getRotationCap(), rightSpeed * RobotMap.getRotationCap());
            System.out.println("turning with Rotation cap " + RobotMap.getRotationCap());
        }
        else { //straight
            Subsystems.driveBase.setMotors(leftSpeed * RobotMap.getSpeedCap(), rightSpeed * RobotMap.getSpeedCap());
            System.out.println("going straight with Speed cap " + RobotMap.getSpeedCap());
        }

        
        // System.out.println("speed cap is " + RobotMap.getSpeedCap());
    }
}