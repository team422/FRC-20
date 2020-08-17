package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.userinterface.UserInterface;
import frc.robot.subsystems.Subsystems;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

/**
 * The main Robot class whence all things come.
 */
public class Robot extends TimedRobot {

    //AUTONOMOUS/SHUFFLEBOARD

    private AutonomousSwitch autonomous;
    private SendableChooser<AutonomousSwitch.StartingPosition> positionChooser;
    private NetworkTableEntry delayChooser;
    private NetworkTableEntry pushRobotChooser;
    private SendableChooser<AutonomousSwitch.IntakeSource> intakeChooser;
    private NetworkTableEntry autoLabel;

    private NetworkTableEntry driverControllerWidget;
    private NetworkTableEntry operatorControllerWidget;

    private NetworkTableEntry cellCountWidget;
    private NetworkTableEntry overflowWidget;

    private NetworkTableEntry leftEncoders;
    private NetworkTableEntry rightEncoders;
    private NetworkTableEntry gyroWidget;
    private NetworkTableEntry intakeBeamBreakWidget;
    private NetworkTableEntry isSpeedModeWidget;
    private NetworkTableEntry isCamera1Widget;
    private NetworkTableEntry isIntakeUpWidget;

    private NetworkTableEntry currentFlywheelWidget;
    private NetworkTableEntry actualFlywheelWidget;

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
        UserInterface.driverController.LB.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2)); //LBump: Toggle cameras
        UserInterface.driverController.RB.whenPressed(new SwitchGears()); //RBump: Toggle slow/fast mode

        //operator controls (buttons)
        UserInterface.operatorController.X.whenPressed(new IntakeExtendRetract()); //X: Toggles extend/retract intake
        UserInterface.operatorController.LS.whileHeld(new Vomit()); //Left small: SPIT WITH ALL YOU HAVE
        UserInterface.operatorController.RS.whenPressed(new ClearCellCount()); //Right small: set cell count to 0
        UserInterface.operatorController.RB.whenPressed(new StartFlywheel(0.7)); //start flywheel early
        UserInterface.operatorController.RB.whenPressed(new HelixTurn(0.3)); //start flywheel early

        autonomous = new AutonomousSwitch(AutonomousSwitch.StartingPosition.CENTER, 0, false, AutonomousSwitch.IntakeSource.TRENCH); //default
        //setup Shuffleboard interface
        layoutShuffleboard();
    }

    public void robotPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        if (AutonomousSwitch.doChoicesWork(positionChooser.getSelected(), intakeChooser.getSelected())) {
            //update auto if changed
            if (!autonomous.matchesSettings(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected())) {
                autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected());
                autoLabel.setString(autonomous.description);
            }
        } else {
            autoLabel.setString("Options don't work. Defaulting to last chosen autonomous (SP=" + autonomous.startingPosition + ", D=" + Math.round(autonomous.delay*100.0)/100.0 +
            ", PR=" + autonomous.pushRobot + ", IS=" + autonomous.intakeSource + ").");
        }
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();

        if (AutonomousSwitch.doChoicesWork(positionChooser.getSelected(), intakeChooser.getSelected())) {
            //update auto
            autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected());
            autoLabel.setString(autonomous.description);
        } else {
            autoLabel.setString("Options don't work. Defaulting to last chosen autonomous (SP=" + autonomous.startingPosition + ", D=" + Math.round(autonomous.delay*100.0)/100.0 +
            ", PR=" + autonomous.pushRobot + ", IS=" + autonomous.intakeSource + ").");
        }
        autonomous.start();
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
     * Arranges the Shuffleboard's layout.
     */
    private void layoutShuffleboard() {
        //Get references to tabs & layouts
        ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-Match");
        ShuffleboardTab matchPlayTab = Shuffleboard.getTab("Match Play");

        ShuffleboardLayout autonomousChooserLayout = preMatchTab.getLayout("Choose an autonomous...", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(5, 3);
        ShuffleboardLayout controllerIDLayout = preMatchTab.getLayout("Identify controllers before switching to next tab", BuiltInLayouts.kList)
            .withPosition(5, 0)
            .withSize(4, 3);
        ShuffleboardLayout sensorValueLayout = matchPlayTab.getLayout("Sensor values", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 4, "number of rows", 3))
            .withPosition(6, 1)
            .withSize(3, 2);
        ShuffleboardLayout controlsLayout = matchPlayTab.getLayout("Controls", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(1, 3);

        //Setup autonomous options and layouts
        positionChooser = new SendableChooser<AutonomousSwitch.StartingPosition>();
        positionChooser.setDefaultOption("Center", AutonomousSwitch.StartingPosition.CENTER);
        positionChooser.addOption("Left", AutonomousSwitch.StartingPosition.LEFT);
        positionChooser.addOption("Right", AutonomousSwitch.StartingPosition.RIGHT);

        intakeChooser = new SendableChooser<AutonomousSwitch.IntakeSource>();
        intakeChooser.setDefaultOption("Trench", AutonomousSwitch.IntakeSource.TRENCH);
        intakeChooser.addOption("Rendevous", AutonomousSwitch.IntakeSource.RENDEZVOUS);
        intakeChooser.addOption("3 from trench and 2 from rendevous", AutonomousSwitch.IntakeSource.MIXED);

        autonomousChooserLayout.add("Starting position", positionChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser);
        delayChooser = autonomousChooserLayout.add("Delay", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 10)).getEntry();
        pushRobotChooser = autonomousChooserLayout.add("Push other robot?", false)
            .withWidget(BuiltInWidgets.kToggleButton).getEntry();
        autonomousChooserLayout.add("Intake source", intakeChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser);
        autoLabel = autonomousChooserLayout.add("Current autonomous", "Starts in center, shoots after a delay of 0, doesn't push robot, intakes from trench").getEntry();

        //Setup controller ID in pre-match
        driverControllerWidget = controllerIDLayout.add("Driver Controller", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("color when false", "#7E8083", "color when true", "#00B259")).getEntry();
        operatorControllerWidget = controllerIDLayout.add("Operator Controller", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("color when false", "#7E8083", "color when true", "#00B259")).getEntry();

        //Setup match play options and layouts
        // ***** ADD FMS INFO WIDGET MANUALLY *****
        // matchPlayTab.add(SendableCameraWrapper.wrap(camera1)) //if 1 camera used
        //     .withWidget(BuiltInWidgets.kCameraStream)
        //     .withPosition(3, 0)
        //     .withSize(3, 3);

        //cell count
        cellCountWidget = matchPlayTab.add("Power cell count", 3)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", 0, "max", 5))
            .withPosition(1, 0)
            .withSize(2, 2).getEntry();
        overflowWidget = matchPlayTab.add("Ball overflow", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("color when false", "#7E8083", "color when true", "#8b0000"))
            .withPosition(1, 2)
            .withSize(2, 1).getEntry();

        //sensor values
        leftEncoders = sensorValueLayout.add("Left encoders", 404).getEntry();
        rightEncoders = sensorValueLayout.add("Right encoders", 404).getEntry();
        gyroWidget = sensorValueLayout.add("Gyro", 404).getEntry();
        intakeBeamBreakWidget = sensorValueLayout.add("Intake beam break", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("color when false", "#7E8083", "color when true", "#ffe815")).getEntry();
        isSpeedModeWidget = sensorValueLayout.add("Speed mode?", false)
            .withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        isCamera1Widget = sensorValueLayout.add("Main camera?", true)
            .withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        isIntakeUpWidget = sensorValueLayout.add("Is intake up?", true)
            .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

        //controls
        currentFlywheelWidget = controlsLayout.add("Current speed:", Subsystems.flyboi.wheelSpeed).getEntry();
        actualFlywheelWidget = controlsLayout.add("Actual speed:", 0).getEntry();
    }

    /**
     * Updates data used in Shuffleboard. This will be updated even if the robot is disabled.
     */
    private void printDataToShuffleboard() {
        //controller id's
        driverControllerWidget.setBoolean(Math.abs(UserInterface.driverController.getLeftJoystickX()) > 0.1 || Math.abs(UserInterface.driverController.getLeftJoystickY()) > 0.1 ||
        Math.abs(UserInterface.driverController.getRightJoystickX()) > 0.1 || Math.abs(UserInterface.driverController.getRightJoystickY()) > 0.1);
        operatorControllerWidget.setBoolean(Math.abs(UserInterface.operatorController.getLeftJoystickX()) > 0.1 || Math.abs(UserInterface.operatorController.getLeftJoystickY()) > 0.1 ||
        Math.abs(UserInterface.operatorController.getRightJoystickX()) > 0.1 || Math.abs(UserInterface.operatorController.getRightJoystickY()) > 0.1);

        //cell count
        cellCountWidget.setDouble(Subsystems.helix.cellCount);
        overflowWidget.setBoolean(Subsystems.helix.cellCount > 5);

        //control panel
        currentFlywheelWidget.setDouble(Subsystems.flyboi.wheelSpeed);
        actualFlywheelWidget.setDouble(Subsystems.flyboi.getPower());

        //sensor values
        leftEncoders.setDouble(Subsystems.driveBase.getLeftPosition());
        rightEncoders.setDouble(Subsystems.driveBase.getRightPosition());
        gyroWidget.setDouble(Subsystems.driveBase.getGyroAngle());
        intakeBeamBreakWidget.setBoolean(Subsystems.intake.getCellEntered());
        isSpeedModeWidget.setBoolean(RobotMap.isSpeedMode);
        isCamera1Widget.setBoolean(RobotMap.isFirstCamera);
        isIntakeUpWidget.setBoolean(!RobotMap.isIntakeDown);
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