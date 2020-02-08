package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.userinterface.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import io.github.pseudoresonance.pixy2api.*;

import java.util.Map;

/**
 * The main Robot class whence all things come.
 */
public class Robot extends TimedRobot {

    private UsbCamera camera;

    private AutonomousSwitch autonomous;
    private SendableChooser<String> positionChooser;
    private NetworkTableEntry delayChooser;
    private NetworkTableEntry pushRobotChooser;
    private SendableChooser<String> intakeChooser;
    private NetworkTableEntry autoLabel;

    private NetworkTableEntry driverControllerWidget;
    private NetworkTableEntry operatorControllerWidget;

    private NetworkTableEntry cellCountWidget;
    private NetworkTableEntry overflowWidget;

    private NetworkTableEntry leftEncoders;
    private NetworkTableEntry rightEncoders;
    private NetworkTableEntry gyroWidget;
    private NetworkTableEntry intakeBeamBreakWidget;

    private NetworkTableEntry blockX;

    private int cellCount = 3; //replace with var from helix

    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        //set which bot
        RobotMap.setBot("practice");
        System.out.println("Initializing " + RobotMap.botName + "\n");

        //start camera capture
        camera = CameraServer.getInstance().startAutomaticCapture();

        //drive settings
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        RobotMap.setSpeedAndRotationCaps(0.3, 0.5);

        autonomous = new AutonomousSwitch("C", 0, false, "trench"); //default
        //setup Shuffleboard interface
        layoutShuffleboard();
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        printDataToShuffleboard();
        Scheduler.getInstance().run();

        //update auto if changed
        if (!autonomous.matchesSettings(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected())) {
            autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected());
            autoLabel.setString(autonomous.description);
        }
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();

        autonomous = new AutonomousSwitch(positionChooser.getSelected(), delayChooser.getDouble(0), pushRobotChooser.getBoolean(false), intakeChooser.getSelected());
        autoLabel.setString(autonomous.description);
        autonomous.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDataToShuffleboard();

        //Run flywheel when operator Y pressed down (change on operator request)
        if (UserInterface.driverController.Y.get()) {
            Subsystems.flyboi.spinWheel(0.5);
        } else {
            Subsystems.flyboi.stopWheel();
        }
    }

    /**
     * Arranges the Shuffleboard's layout.
     */
    private void layoutShuffleboard() {
        //Get references to tabs & layouts
        ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-Match");
        ShuffleboardTab matchPlayTab = Shuffleboard.getTab("Match Play");

        ShuffleboardLayout autonomousChooserLayout = preMatchTab.getLayout("Choose an autonomous...", BuiltInLayouts.kList).withPosition(0, 0).withSize(5, 3);
        ShuffleboardLayout controllerIDLayout = preMatchTab.getLayout("Identify controllers before switching to next tab", BuiltInLayouts.kList).withPosition(5, 0).withSize(4, 3);
        ShuffleboardLayout sensorValueLayout = matchPlayTab.getLayout("Sensor values", BuiltInLayouts.kGrid).withProperties(Map.of("number of columns", 4, "number of rows", 3)).withPosition(6, 1).withSize(3, 2);
        ShuffleboardLayout pixyLayout = matchPlayTab.getLayout("pixy", BuiltInLayouts.kList).withPosition(0, 0).withSize(1, 3);

        //Setup autonomous options and layouts
        positionChooser = new SendableChooser<String>();
        positionChooser.setDefaultOption("Center", "C");
        positionChooser.addOption("Left", "L");
        positionChooser.addOption("Right", "R");

        intakeChooser = new SendableChooser<String>();
        intakeChooser.setDefaultOption("Trench", "trench");
        intakeChooser.addOption("Rendevous", "rendevous");
        intakeChooser.addOption("3 from trench and 2 from rendevous", "mixed");
        intakeChooser.addOption("None (get out of way)", "none");

        autonomousChooserLayout.add("Starting position", positionChooser).withWidget(BuiltInWidgets.kComboBoxChooser);
        delayChooser = autonomousChooserLayout.add("Delay", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 10)).getEntry();
        pushRobotChooser = autonomousChooserLayout.add("Push other robot?", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        autonomousChooserLayout.add("Intake source", intakeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);
        autoLabel = autonomousChooserLayout.add("Current autonomous", "Starts in center, shoots after a delay of 0, doesn't push robot, intakes from trench").getEntry();

        //Setup controller ID in pre-match
        driverControllerWidget = controllerIDLayout.add("Driver Controller", false).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("color when false", "#808080")).getEntry();
        operatorControllerWidget = controllerIDLayout.add("Operator Controller", false).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("color when false", "#808080")).getEntry();

        //Setup match play options and layouts
        // ***** ADD FMS INFO WIDGET MANUALLY *****
        matchPlayTab.add(SendableCameraWrapper.wrap(camera)).withWidget(BuiltInWidgets.kCameraStream).withPosition(3, 0).withSize(3, 3);

        //cell count - add highest ones first
        cellCountWidget = matchPlayTab.add("Power cell count", 3).withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).withPosition(1, 0).withSize(2, 2).getEntry();
        overflowWidget = matchPlayTab.add("Power cell overflow", false).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("color when false", "#99cc99", "color when true", "#8b0000")).withPosition(1, 2).withSize(2, 1).getEntry();

        //sensor values
        leftEncoders = sensorValueLayout.add("Left encoders", 404).withPosition(0, 0).getEntry();
        rightEncoders = sensorValueLayout.add("Right encoders", 404).withPosition(1, 0).getEntry();
        gyroWidget = sensorValueLayout.add("Gyro", 404).withPosition(2, 0).getEntry();
        intakeBeamBreakWidget = sensorValueLayout.add("Intake beam break", false).withPosition(3, 0).getEntry();

        //pixy
        blockX = pixyLayout.add("blockX", 404).getEntry();
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
        cellCountWidget.setDouble(cellCount);
        overflowWidget.setBoolean(cellCount > 5);

        //sensor values
        leftEncoders.setDouble(Subsystems.driveBase.getLeftPosition());
        rightEncoders.setDouble(Subsystems.driveBase.getRightPosition());
        gyroWidget.setDouble(Subsystems.driveBase.getGyroAngle());
        intakeBeamBreakWidget.setBoolean(false); //change when intake exists

        //pixy values
        try {
            Pixy2CCC.Block block = Subsystems.pixy.getBiggestBlock();
            blockX.setDouble(block.getX());
        } catch (java.lang.NullPointerException e) {
            blockX.setDouble(-404);
            return;
        }
    }
}