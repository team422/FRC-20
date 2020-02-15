package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.userinterface.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import io.github.pseudoresonance.pixy2api.*;

/**
 * The main Robot class whence all things come.
 */
public class Robot extends TimedRobot {

    private UsbCamera camera;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private CenterTrench autonomous;
    private Alliance currentAlliance;
    int ledTimer = 0;
    


    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        RobotMap.setBot(RobotMap.BotNames.TOASTER);
        System.out.println("Initializing " + RobotMap.botName + "\n");
        
        led = new AddressableLED(0);
        ledBuffer = new AddressableLEDBuffer(96);
        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();

        camera = CameraServer.getInstance().startAutomaticCapture();

        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        RobotMap.setSpeedAndRotationCaps(0.3, 0.5);
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
        
        for(int i = 0; i<ledBuffer.getLength(); i++){
            ledBuffer.setRGB(i, 0, 178, 0);
        }
        ledBuffer.setRGB(95, 178, 0, 0);
        led.setData(ledBuffer);
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
        Subsystems.driveBase.zeroEncoderPosition();
        currentAlliance = DriverStation.getInstance().getAlliance();
        
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

        
        if (currentAlliance == Alliance.Red){
            for(int i = 0; i<ledBuffer.getLength(); i++){
                if (i%10<5){
                    ledBuffer.setRGB(i, 100, 0, 0);
                }
                else{
                    ledBuffer.setRGB(i,0,100,0);
                }
            }
        }
        else if (currentAlliance == Alliance.Blue){
            for (int i = 0; i < ledBuffer.getLength(); i++){
                ledBuffer.setRGB(i, 0, 0, 100);
            }
        }


        led.setData(ledBuffer);

        ledTimer++;
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