package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.userinterface.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import io.github.pseudoresonance.pixy2api.*;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;

/**
 * The main Robot class whence all things come.
 */
public class Robot extends TimedRobot {

    private VideoSink switchedCamera;
    private UsbCamera camera1;
    private UsbCamera camera2;
    private CenterTrench autonomous;

    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        RobotMap.setBot(RobotMap.BotNames.TOASTER);
        System.out.println("Initializing " + RobotMap.botName + "\n");

        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        switchedCamera = CameraServer.getInstance().addSwitchedCamera("Camera feeds");
        switchedCamera.setSource(camera1);

        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        printDataToSmartDashboard();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();

        autonomous = new CenterTrench();
        autonomous.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();

        switchedCamera.setSource(camera1);

        UserInterface.driverController.A.whenPressed(new SwitchGears());
        UserInterface.driverController.LB.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2));
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();

        //Run flywheel when operator Y pressed down (change on operator request)
        if (UserInterface.driverController.Y.get()) {
            Subsystems.flyboi.spinWheel(0.5);
        } else {
            Subsystems.flyboi.stopWheel();
        }
    }

    /**
     * Puts data into the Smart Dashboard. This will be updated even if the robot is disabled.
     */
    private void printDataToSmartDashboard() {
        try {
            Pixy2CCC.Block block = Subsystems.pixy.getBiggestBlock();
            SmartDashboard.putNumber("blockX", block.getX());
        } catch (java.lang.NullPointerException e) {
            return;
        }
    }
}