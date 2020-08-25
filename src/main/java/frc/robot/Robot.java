package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
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

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        //set which bot - either COMPETITION, PRACTICE, or TOASTER
        RobotMap.setBot(RobotMap.BotNames.COMPETITION);
        System.out.println("Initializing " + RobotMap.botName + "\n");

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
        UserInterface.leftJoystix.bumper.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2)); //LBump: Toggle cameras
        UserInterface.rightJoystix.bumper.whenPressed(new SwitchGears()); //RBump: Toggle slow/fast mode

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