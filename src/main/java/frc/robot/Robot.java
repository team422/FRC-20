package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.userinterface.UserInterface;
import frc.robot.subsystems.Subsystems;
import frc.robot.commands.*;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;

/**
 * The main Robot class whence all things come.
 */
public class Robot extends TimedRobot {

    //TELEOP

    private boolean oldBroken = false;
    private boolean in = false;
    private int counter = 0;

    private boolean oldTriggerOn = false;

    //SENSORS/CAMERAS

    private VideoSink switchedCamera;
    private UsbCamera camera1;
    private UsbCamera camera2;

    private AddressableLED ledHelix;
    private AddressableLEDBuffer ledBufferHelix;
    private AddressableLED ledShooter;
    private AddressableLEDBuffer ledBufferShooter;
    private Alliance currentAlliance;
    int ledTimer = 0;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        //set which bot - either COMPETITION, PRACTICE, or TOASTER
        RobotMap.setBot(RobotMap.BotNames.COMPETITION);
        System.out.println("Initializing " + RobotMap.botName + "\n");

        //led setup for Helix
        ledHelix = new AddressableLED(1);
        ledBufferHelix = new AddressableLEDBuffer(80);
        ledHelix.setLength(ledBufferHelix.getLength());

        ledHelix.setData(ledBufferHelix);
        ledHelix.start();

        //led setup for Shooter
        ledShooter = new AddressableLED(2);
        ledBufferShooter = new AddressableLEDBuffer(12);
        ledShooter.setLength(ledBufferShooter.getLength());

        ledShooter.setData(ledBufferShooter);
        ledShooter.start();

        //camera setup
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        switchedCamera = CameraServer.getInstance().addSwitchedCamera("Camera feeds");
        switchedCamera.setSource(camera1);

        //drive settings
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        
        //driver controls (buttons)
        UserInterface.driverController.LB.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2)); //LBump: Toggle cameras
        UserInterface.driverController.RB.whenPressed(new SwitchGears()); //RBump: Toggle slow/fast mode

        //operator controls (buttons)
        UserInterface.operatorController.X.whenPressed(new IntakeExtendRetract()); //X: Toggles extend/retract intake
        UserInterface.operatorController.LS.whileHeld(new Vomit()); //Left small: SPIT WITH ALL YOU HAVE
        UserInterface.operatorController.RS.whenPressed(new ClearCellCount()); //Right small: set cell count to 0
        UserInterface.operatorController.RB.whenPressed(new StartFlywheel(0.7)); //start flywheel early
        UserInterface.operatorController.RB.whenPressed(new HelixTurn(0.3)); //start flywheel early

        //setup Shuffleboard interface & default auto
        ShuffleboardControl.layoutShuffleboard();
        ShuffleboardControl.setupAutonomous();
    }

    public void robotPeriodic() {
        Scheduler.getInstance().run();
        ShuffleboardControl.printDataToShuffleboard();
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
        
        for(int i = 0; i<ledBufferHelix.getLength(); i++){
            ledBufferHelix.setRGB(i, 0, 178, 0);
        }
        ledBufferHelix.setRGB(95, 178, 0, 0);
        ledHelix.setData(ledBufferHelix);

        for(int i = 0; i<ledBufferShooter.getLength(); i++){
            ledBufferShooter.setRGB(i, 0, 178, 0);
        }
        ledBufferShooter.setRGB(95, 178, 0, 0);
        ledShooter.setData(ledBufferShooter);
    }

    public void disabledPeriodic() {
        ShuffleboardControl.updateAutonomous();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();

        ShuffleboardControl.updateAutonomous();
        ShuffleboardControl.getAutonomous().start();
    }

    public void autonomousPeriodic() {
        countingAuto();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();
        currentAlliance = DriverStation.getInstance().getAlliance();

        Scheduler.getInstance().add(new ShootStop()); //in case was disabled while spinning

        switchedCamera.setSource(camera1);
        RobotMap.isFirstCamera = true;
    }

    public void teleopPeriodic() {
        countingTeleop();

        //wait for intake->helix sequence
        if (in && counter < 9) {
            counter++;
        } else if (in) {
            in = false;
            counter = 0;
        }

        if (UserInterface.operatorController.LS.get()) {
            return; //remove functionality of spinning while vomiting
        }

        //intake cells in/out
        if (UserInterface.operatorController.getRightJoystickY() >= 0.4) {
            Subsystems.intake.setIntakeMotors(0.85);
        } else if (UserInterface.operatorController.getRightJoystickY() <= -0.4) {
            Subsystems.intake.setIntakeMotors(-0.85);
        } else {
            Subsystems.intake.stopIntakeMotors();
        }

        
        if (currentAlliance == Alliance.Red) {
            for(int i = 0; i<(Subsystems.flyboi.getPower()/Subsystems.flyboi.wheelSpeed)*ledBufferShooter.getLength() && i< ledBufferShooter.getLength(); i++) {
                ledBufferShooter.setRGB(i, 100, 0, 0);
            }
            for(int i = 0; i < ledBufferShooter.getLength(); i++){
                ledBufferHelix.setRGB(i,100,0,0);
            }
        }
        else if (currentAlliance == Alliance.Blue) {
            for(int i = 0; i<(Subsystems.flyboi.getPower()/Subsystems.flyboi.wheelSpeed)*ledBufferShooter.getLength()&& i< ledBufferShooter.getLength(); i++) {
                ledBufferShooter.setRGB(i, 0, 0, 100);
            }
            for(int i = 0; i < ledBufferShooter.getLength(); i++){
                ledBufferHelix.setRGB(i,0,0,100);
            }
        }
        ledShooter.setData(ledBufferShooter);
        ledHelix.setData(ledBufferHelix);
        ledTimer++;
        //flyboi control
        boolean isTriggerOn = UserInterface.operatorController.getRightTrigger() >= 0.4;
        if (isTriggerOn && !oldTriggerOn) { //if trigger was just pressed
            Scheduler.getInstance().add(new Shoot());
            System.out.println("Shooter speed is " + Subsystems.flyboi.getPower());
        } else if (!isTriggerOn && oldTriggerOn) { //if trigger was just released
            Scheduler.getInstance().add(new ShootStop());
        }
        oldTriggerOn = isTriggerOn;

        //moves helix in/out
        if (UserInterface.operatorController.getPOVAngle() == 0) {
            Subsystems.helix.setHelixMotors(0.9);
        } else if (UserInterface.operatorController.getPOVAngle() == 180) {
            Subsystems.helix.setHelixMotors(-0.9);
        } else if (UserInterface.operatorController.Y.get()) {
            Subsystems.helix.setHelixMotors(-0.5);
        } else if (in) {
            Subsystems.helix.setHelixMotors(0.75);
        } else if (!isTriggerOn) {
            Subsystems.helix.setHelixMotors(0);
        }
    }

    /**
     * Counts cells intaken in auto.
     */
    private void countingAuto() {
        boolean isBroken = Subsystems.intake.getCellEntered();

        if (isBroken && !oldBroken) {
            Subsystems.helix.cellCount++;
        }
        oldBroken = isBroken;
    }

    /**
     * Counts cells intaken or expelled in teleop.
     */
    private void countingTeleop() {
        boolean isBroken = Subsystems.intake.getCellEntered();

        if (UserInterface.operatorController.getRightJoystickY() >= 0.4) { //if is intaking
            if (isBroken && !oldBroken) {
                Subsystems.helix.cellCount++;
                System.out.println("BALL INTAKEN, " + Subsystems.helix.cellCount + " BALLS CONTAINED");
            } else if (oldBroken) {
                in = true;
                counter = 0;
            }
        }
        if (UserInterface.operatorController.getRightJoystickY() <= -0.4) { //if is outtaking
            if (!isBroken && oldBroken) {
                Subsystems.helix.cellCount--;
                System.out.println("BALL OUTTAKEN, " + Subsystems.helix.cellCount + " BALLS REMAINING");
            }
        }

        oldBroken = isBroken;
    }
}