package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.userinterface.UserInterface;
import frc.robot.commands.*;

import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.links.*;

public class Robot extends TimedRobot {

    private UsbCamera camera;

    // private NetworkTableEntry blockX;
    // private NetworkTableEntry blockY;

    private TrackObject trackObject;

    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        RobotMap.setBot("toaster");

        camera = CameraServer.getInstance().startAutomaticCapture();
        
        // NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // NetworkTable pie = inst.getTable("pie");
        // blockX = pie.getEntry("blockX");
        // blockY = pie.getEntry("blockY");

        trackObject = new TrackObject();
      
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        
        Subsystems.linePixy.printIntersection();
        
        
        printDataToSmartDashboard();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();
        trackObject.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        // pixy.readPixy();
        printDataToSmartDashboard();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();

        UserInterface.driverController.X.whileHeld(trackObject);
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    

    private void printDataToSmartDashboard() {



        // Pixy2CCC.Block biggestBlock;
        // try {
        //     biggestBlock = Subsystems.colorPixy.getBiggestBlock();
        //     biggestBlock.print();
        // } catch (java.lang.NullPointerException e) {
        //     return;
        // }
        
        //SmartDashboard.putNumber("blockX", pixy.getBiggestBlock().getX());
        //SmartDashboard.putNumber("blockY", pixy.getBiggestBlock().getY());
    }
}