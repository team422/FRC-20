package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**Uses joystick values to drive the bot in teleop.
*/
public class TankDrive extends Command {

    private static final double speedCap = 0.8;
    private double updatedLeftSpeed = 0;
    private double updatedRightSpeed = 0;
    private static final double maxChange = 0.5; //maxChange is acceleration

    public TankDrive() {
        super("TankDrive");
        requires(Subsystems.driveBase);      
    }

    @Override
    protected void initialize() {}
  
    @Override
    protected void execute() {
        double leftSpeed = UserInterface.leftJoystix.getJoystickY();
        double rightSpeed = UserInterface.rightJoystix.getJoystickY();
        boolean rightTrigger = UserInterface.rightJoystix.TRIGGERED.get();

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