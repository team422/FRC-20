package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    private Command wait = new WaitCommand(2);

    private UsbCamera camera;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        System.out.println("Initializing " + RobotMap.botName + "\n");

        camera = CameraServer.getInstance().startAutomaticCapture();

        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
        /**
         * This makes sure that all of the motors are set to 0% following disable
         */
    }

    public void disabledPeriodic() {
        printDataToSmartDashboard();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        /**
         * This makes sure that any old commands/command groups are stopped upon
         * Autonomous Initialization.
         */
        Scheduler.getInstance().removeAll();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");

        Scheduler.getInstance().removeAll();
    }

    public void teleopPeriodic() {
        /**
         * This makes sure that TankDrive and other Commands used during TeleOp are run.
         */
        Scheduler.getInstance().run();

        printDataToSmartDashboard();
    }

    private void printDataToSmartDashboard() {}
}