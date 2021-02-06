package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Uses joystick values to drive the bot in teleop.
 */
public class TankDrive extends CommandBase {

    private double updatedSpeed = 0;
    private double updatedRotation = 0;
    private final double maxSpeedChange = 0.325; //maxChange is acceleration
    private final double maxRotationChange = 0.15;

    public TankDrive() {
        setName("TankDrive");
        addRequirements(Subsystems.driveBase);
    }

    public void execute() {
        double speed;
        double rotation;

        /* Sets throttle for driveBase to the left stick Y-axis and sets the rotation
        * for driveBase to the right stick X-axis on on the driverXboxController */
        if (UserInterface.driverController.getRightJoystickY() < -0.1) {
            speed = (Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
        } else if (UserInterface.driverController.getRightJoystickY() > 0.1) {
            speed = -(Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
        } else {
            speed = 0;
        }
        if (UserInterface.driverController.getLeftJoystickX() < -0.05) {
            rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
        } else if (UserInterface.driverController.getLeftJoystickX() > 0.05) {
            rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
        } else {
            rotation = 0;
        }
        double speedDifference = speed - updatedSpeed;
        if (speedDifference > maxSpeedChange) {
            speed = updatedSpeed + maxSpeedChange;
        } else if (speedDifference < -maxSpeedChange) {
            speed = updatedSpeed - maxSpeedChange;
        }
        double rotationDifference = rotation - updatedRotation;
        if (rotationDifference > maxRotationChange) {
            rotation = updatedRotation + maxRotationChange;
        } else if (rotationDifference < -maxRotationChange) {
            rotation = updatedRotation - maxRotationChange;
        }

        updatedSpeed = speed;
        updatedRotation = rotation;

        /*  Because of a weird glitch with how curvatureDrive is set up,
         *  the rotation actually goes in as the first input, followed by the speed,
         *  rather than speed then rotation */
        Subsystems.driveBase.cheesyDrive.curvatureDrive(RobotMap.getRotationCap() * -rotation, RobotMap.getSpeedCap() * speed, true);
    }
}