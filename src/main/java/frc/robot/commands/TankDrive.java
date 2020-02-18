package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import io.github.pseudoresonance.pixy2api.*;
import frc.robot.RobotMap;

/**Uses joystick values to drive the bot in teleop.
*/
public class TankDrive extends Command {

    private double updatedLeftSpeed = 0;
    private double updatedRightSpeed = 0;
    private static final double maxChange = 0.5; //maxChange is acceleration
    private final int frameWidth;

    public TankDrive() {
        super("TankDrive");
        requires(Subsystems.driveBase);

        frameWidth = Subsystems.pixy.getFrameWidth();
    }

    @Override
    protected void initialize() {}
  
    @Override
    protected void execute() {

        if (UserInterface.operatorController.B.get()) {
            Pixy2CCC.Block block = Subsystems.pixy.getBiggestBlock();
            if (block != null) {
                System.out.println(block);
                if (block.getX() > (frameWidth / 2)) {
                    Subsystems.driveBase.setMotors(0.1, 0.3); //consider adding speed to right motors
                    return;
                } else if (block.getX() < (frameWidth / 2)) {
                    Subsystems.driveBase.setMotors(0.3, 0.1); //consider adding speed to left motors
                    return;
                } else if (block.getWidth() > 20) {
                    Subsystems.driveBase.setMotors(0.1, 0.1);
                    return;
                } else {
                    System.out.println("Too small boi");
                }
            } else {
                System.out.println("No blocks found");
            }
            UserInterface.operatorController.setRumble(0.5);
        } else {
            //TODO: if count < 5
            //Sets the amount the controller rumbles when near a ball
            try {
                double blockSize = Subsystems.pixy.getBiggestBlock().getWidth();
                double rumbleFactor = blockSize/frameWidth;
                UserInterface.operatorController.setRumble(rumbleFactor);
            } catch (java.lang.NullPointerException e) {
                UserInterface.operatorController.setRumble(0);
            }
        }

        double leftSpeed, rightSpeed;

        /* Sets throttle for driveBase to the left stick Y-axis and sets the rotation
        * for driveBase to the right stick X-axis on on the driverXboxController */
        if (UserInterface.leftJoystix.getJoystickY() < -0.1) {
            leftSpeed = -(Math.pow(UserInterface.leftJoystix.getJoystickY(), 2));
        } else if (UserInterface.leftJoystix.getJoystickY() > 0.1) {
            leftSpeed = (Math.pow(UserInterface.leftJoystix.getJoystickY(), 2));
        } else {
            leftSpeed = 0;
        }

        if (UserInterface.rightJoystix.getJoystickY() < -0.1) {
            rightSpeed = -(Math.pow(UserInterface.rightJoystix.getJoystickY(), 2));
        } else if (UserInterface.rightJoystix.getJoystickY() > 0.1) {
            rightSpeed = (Math.pow(UserInterface.rightJoystix.getJoystickY(), 2));
        } else {
            rightSpeed = 0;
        }

        updatedLeftSpeed = leftSpeed;
        updatedRightSpeed = rightSpeed;

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

        Subsystems.driveBase.setMotors(leftSpeed * RobotMap.speedCap, rightSpeed * RobotMap.speedCap);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {}

    @Override
    protected void end() {}
}