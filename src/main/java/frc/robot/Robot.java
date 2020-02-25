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
import io.github.pseudoresonance.pixy2api.*;
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
    private NetworkTableEntry enableVisionChooser;

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

    private NetworkTableEntry blockX;

    private boolean oldBroken = false;
    private boolean in = false;
    private int counter = 0;

    //SENSORS/CAMERAS

    private VideoSink switchedCamera;
    private UsbCamera camera1;
    private UsbCamera camera2;

    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        //set which bot
        RobotMap.setBot(RobotMap.BotNames.PRACTICE);
        System.out.println("Initializing " + RobotMap.botName + "\n");

        Subsystems.compressor.start();

        //camera setup (not used in week 0)
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        switchedCamera = CameraServer.getInstance().addSwitchedCamera("Camera feeds");
        switchedCamera.setSource(camera1);

        //drive settings
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        RobotMap.setSpeedAndRotationCaps(0.3, 0.5);

        autonomous = new AutonomousSwitch(AutonomousSwitch.StartingPosition.CENTER, 0, false, AutonomousSwitch.IntakeSource.TRENCH, false); //default
        //setup Shuffleboard interface
        layoutShuffleboard();
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();

        if (AutonomousSwitch.doChoicesWork(positionChooser.getSelected(), intakeChooser.getSelected())) {
            //update auto if changed
            if (!autonomous.matchesSettings(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected(), enableVisionChooser.getBoolean(false))) {
                autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected(), enableVisionChooser.getBoolean(false));
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
            autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected(), enableVisionChooser.getBoolean(false));
            autoLabel.setString(autonomous.description);
        } else {
            autoLabel.setString("Options don't work. Defaulting to last chosen autonomous (SP=" + autonomous.startingPosition + ", D=" + Math.round(autonomous.delay*100.0)/100.0 +
            ", PR=" + autonomous.pushRobot + ", IS=" + autonomous.intakeSource + ").");
        }
        autonomous.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();
        countingAuto();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();

        switchedCamera.setSource(camera1);

        //Driver controls
        UserInterface.driverController.LB.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2)); //LBump: Toggle cameras
        UserInterface.driverController.RB.whenPressed(new SwitchGears()); //RBump: Toggle slow/fast mode

        //Operator controls
        UserInterface.operatorController.RB.whenPressed(new Shoot()); //RB: starts the fly shoot command
        UserInterface.operatorController.RB.whenReleased(new ShootStop());
        UserInterface.operatorController.X.whenPressed(new IntakeExtendRetract());
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();
        countingTeleop();

        if (in && counter < 25) {
            counter++;
        } else if (in && counter >= 25) {
            in = false;
            counter = 0;
        }

        // Intake cells in/out
        if (UserInterface.operatorController.getRightJoystickY() >= 0.4) {
            Subsystems.intake.setIntakeMotors(0.8);
        } else if (UserInterface.operatorController.getRightJoystickY() <= -0.4) {
            Subsystems.intake.setIntakeMotors(-0.8);
        } else {
            Subsystems.intake.stopIntakeMotors();
        }

        //moves helix in/out 
        if (UserInterface.operatorController.getPOVAngle() == 0 || in){
            Subsystems.helix.setHelixMotors(0.8);
        } else if (UserInterface.operatorController.getPOVAngle() == 180) {
            Subsystems.helix.setHelixMotors(-0.8);
        } else if (!UserInterface.operatorController.RB.get()) {
            Subsystems.helix.setHelixMotors(0);
        }

        //moves robot up and down during climbing
        // if (UserInterface.operatorController.getLeftJoystickY() >= 0.4){
        //     Subsystems.climber.setClimberMotors(0.8);
        // } else if (UserInterface.operatorController.getLeftJoystickY() <= -0.4) {
        //     Subsystems.climber.setClimberMotors(-0.8);
        // } else {
        //     Subsystems.climber.setClimberMotors(0);
        // }
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
        ShuffleboardLayout visionLayout = matchPlayTab.getLayout("vision", BuiltInLayouts.kList)
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
        enableVisionChooser = autonomousChooserLayout.add("Enable vision mode?", false)
            .withWidget(BuiltInWidgets.kToggleButton).getEntry();

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


        //vision
        blockX = visionLayout.add("blockX", 404).getEntry();


        // Buttons tab

        ShuffleboardTab buttonTab = Shuffleboard.getTab("Buttons");

        ShuffleboardLayout driverButtonsLayout = buttonTab.getLayout("Driver Controller", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 3, "number of rows", 3))
            .withPosition(0, 0)
            .withSize(4, 3);
        ShuffleboardLayout operatorButtonsLayout = buttonTab.getLayout("Operator Controller", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 4, "number of rows", 3))
            .withPosition(4, 0)
            .withSize(5, 3);

        ShuffleboardLayout driverUpperLeftLayout = driverButtonsLayout.getLayout("Driver upper left layout", BuiltInLayouts.kList);
            driverUpperLeftLayout.add("Left trigger", "");
            driverUpperLeftLayout.add("Left bumper", "Toggle camera");
        driverButtonsLayout.add("Left joystick", "Rotation"); //middle left
        ShuffleboardLayout driverLowerLeftLayout = driverButtonsLayout.getLayout("Driver lower left layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            driverLowerLeftLayout.add("POV ^", "");
            driverLowerLeftLayout.add("POV v", "");
        ShuffleboardLayout driverUpperMiddleLayout = driverButtonsLayout.getLayout("Driver upper middle layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            driverUpperMiddleLayout.add("Left small", "");
            driverUpperMiddleLayout.add("Right small", "");
        driverButtonsLayout.add("", ""); //placeholder for true neutral
        driverButtonsLayout.add("Right joystick", "Velocity"); //lower middle
        ShuffleboardLayout driverUpperRightLayout = driverButtonsLayout.getLayout("Driver upper right layout", BuiltInLayouts.kList);
            driverUpperRightLayout.add("Right trigger", "");
            driverUpperRightLayout.add("Right bumper", "Toggle slow/fast");
        ShuffleboardLayout driverMiddleRightLayout = driverButtonsLayout.getLayout("Driver middle right layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            driverMiddleRightLayout.add("X", "");
            driverMiddleRightLayout.add("Y", "");
        ShuffleboardLayout driverLowerRightLayout = driverButtonsLayout.getLayout("Driver lower right layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            driverLowerRightLayout.add("A", "Intake and vision takeover");
            driverLowerRightLayout.add("B", "");

        ShuffleboardLayout operatorUpperLeftLayout = operatorButtonsLayout.getLayout("Operator upper left layout", BuiltInLayouts.kList);
            operatorUpperLeftLayout.add("Left trigger", "");
            operatorUpperLeftLayout.add("Left bumper", "");
        operatorButtonsLayout.add("Left joystick", "Intake in/out"); //middle left
        ShuffleboardLayout operatorLowerLeftLayout = operatorButtonsLayout.getLayout("Operator lower left layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            operatorLowerLeftLayout.add("POV ^", "");
            operatorLowerLeftLayout.add("POV v", "");
        ShuffleboardLayout operatorUpperMiddleLayout = operatorButtonsLayout.getLayout("Operator upper middle layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            operatorUpperMiddleLayout.add("Left small", "");
            operatorUpperMiddleLayout.add("Right small", "");
        operatorButtonsLayout.add("", ""); //placeholder for true neutral
        operatorButtonsLayout.add("Right joystick", "Helix in/out"); //lower middle
        ShuffleboardLayout operatorUpperRightLayout = operatorButtonsLayout.getLayout("Operator upper right layout", BuiltInLayouts.kList);
            operatorUpperRightLayout.add("Right trigger", "Retract climber");
            operatorUpperRightLayout.add("Right bumper", "Extend climber");
        ShuffleboardLayout operatorMiddleRightLayout = operatorButtonsLayout.getLayout("Operator middle right layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            operatorMiddleRightLayout.add("X", "Shooter on/off");
            operatorMiddleRightLayout.add("Y", "Climber brake toggle");
        ShuffleboardLayout operatorLowerRightLayout = operatorButtonsLayout.getLayout("Operator lower right layout", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
            operatorLowerRightLayout.add("A", "Intake extend/retract");
            operatorLowerRightLayout.add("B", "");
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
        cellCountWidget.setDouble(Subsystems.intake.cellCount);
        overflowWidget.setBoolean(Subsystems.intake.cellCount > 5);

        //sensor values
        leftEncoders.setDouble(Subsystems.driveBase.getLeftPosition());
        rightEncoders.setDouble(Subsystems.driveBase.getRightPosition());
        gyroWidget.setDouble(Subsystems.driveBase.getGyroAngle());
        intakeBeamBreakWidget.setBoolean(Subsystems.intake.getCellEntered());
        isSpeedModeWidget.setBoolean(RobotMap.isSpeedMode);
        isCamera1Widget.setBoolean(RobotMap.isFirstCamera);

        //pixy values
        try {
            Pixy2CCC.Block block = Subsystems.pixy.getBiggestBlock();
            blockX.setDouble(block.getX());
        } catch (java.lang.NullPointerException e) {
            blockX.setDouble(-404);
            return;
        }
    }

    private void countingAuto() {
        boolean isBroken = Subsystems.intake.getCellEntered();

        if (isBroken && !oldBroken) {
            Subsystems.intake.cellCount++;
        }
        oldBroken = isBroken;
    }

    private void countingTeleop() {
        boolean isBroken = Subsystems.intake.getCellEntered();

        if (UserInterface.operatorController.getRightJoystickY() >= 0.4) { //if is intaking
            if (isBroken && !oldBroken) {
                Subsystems.intake.cellCount++;
                in = true;
            }
        }
        if (UserInterface.operatorController.getRightJoystickY() <= -0.4) { //if is outtaking
            if (!isBroken && oldBroken) {
                Subsystems.intake.cellCount--;
            }
        }

        oldBroken = isBroken;
    }
}