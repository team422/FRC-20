package frc.robot;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.autonomous.*;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class ShuffleboardControl {

    private static Autonomous autonomous;

    private static SendableChooser<String> autoChooserWidget;
    private static NetworkTableEntry selectedAutoLabelWidget;
    private static NetworkTableEntry autoSetupSuccessfulWidget;
    private static NetworkTableEntry pathWidget;
    private static NetworkTableEntry countdownWidget;
    private static NetworkTableEntry recordingTimerWidget;
    private static NetworkTableEntry filenameWidget;

    private static NetworkTableEntry setSpeedWidget;
    private static NetworkTableEntry actualSpeedWidget;
    private static NetworkTableEntry cellCountWidget;
    private static NetworkTableEntry ballOverflowWidget;
    private static NetworkTableEntry matchTimeWidget;
    private static NetworkTableEntry encodersWidget;
    private static NetworkTableEntry gyroWidget;
    private static NetworkTableEntry beamBreakWidget;
    private static NetworkTableEntry isCamera1Widget;
    private static NetworkTableEntry isFastModeWidget;
    private static NetworkTableEntry isIntakeUpWidget;

    private static NetworkTableEntry operatorControllerWidget;
    private static NetworkTableEntry driverControllerWidget;

    // SHUFFLEBOARD

    /**
     * Arranges the Shuffleboard layout.
     */
    public static void layoutShuffleboard() {
        //Get references to tabs & layouts
        ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");
        ShuffleboardTab teleopTab = Shuffleboard.getTab("Teleoperated");
        ShuffleboardTab buttonsTab = Shuffleboard.getTab("Buttons");

        ShuffleboardLayout chooseAutoLayout = autonomousTab.getLayout("Choose auto", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(2, 3);
        ShuffleboardLayout recordAutoLayout = autonomousTab.getLayout("Record new auto", BuiltInLayouts.kList)
            .withProperties(Map.of("label position", "HIDDEN"))
            .withPosition(0, 0)
            .withSize(2, 3);
        ShuffleboardLayout timeValueLayout = teleopTab.getLayout("Match time & Sensor values", BuiltInLayouts.kList)
            .withProperties(Map.of("label position", "HIDDEN"))
            .withPosition(6, 1)
            .withSize(3, 2);
        ShuffleboardLayout controlsLayout = teleopTab.getLayout("Controls", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(1, 3);

        //Setup autonomous tab

        autoChooserWidget = new SendableChooser<String>();
        ShuffleboardControl.updateAutoFiles(autoChooserWidget);

        chooseAutoLayout.add("Browse autos", autoChooserWidget)
            .withWidget(BuiltInWidgets.kComboBoxChooser);
        chooseAutoLayout.add("Choose selected", new SetAuto())
            .withWidget(BuiltInWidgets.kCommand);
        selectedAutoLabelWidget = chooseAutoLayout.add("Currently selected auto:", "Default").getEntry();

        autoSetupSuccessfulWidget = autonomousTab.add("Auto setup successful", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withPosition(0, 2)
            .withSize(2, 1).getEntry();
        
        pathWidget = autonomousTab.add("Path", "") //TODO
            .withWidget("Path")
            .withPosition(2, 0)
            .withSize(3, 3).getEntry();

        ShuffleboardLayout startCancelRecordingLayout = recordAutoLayout.getLayout("Start/cancel recording", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1, "label position", "HIDDEN"));
        startCancelRecordingLayout.add("Start", new StartRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 0);
        startCancelRecordingLayout.add("Cancel", new CancelRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(1, 0);
        
        countdownWidget = recordAutoLayout.add("Countdown to recording", "").getEntry();

        recordingTimerWidget = recordAutoLayout.add("Time passed since recording", 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 0, "max", 15, "num tick marks", 4)).getEntry();
        
        recordAutoLayout.add("Replay", new ReplayRecording())
            .withWidget(BuiltInWidgets.kCommand);
        
        ShuffleboardLayout saveRecordingLayout = recordAutoLayout.getLayout("Save recording", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1));
        saveRecordingLayout.add("Save as", new SaveRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 0);
        filenameWidget = saveRecordingLayout.add("Filename", "").getEntry();

        recordAutoLayout.add("Discard", new DiscardRecording())
            .withWidget(BuiltInWidgets.kCommand);

        // Setup teleoperated tab
        // ***** ADD CAMERA & FMS INFO WIDGET MANUALLY *****

        setSpeedWidget = controlsLayout.add("Set speed:", 0).getEntry();
        actualSpeedWidget = controlsLayout.add("Actual speed:", 0).getEntry();

        cellCountWidget = teleopTab.add("Power cell count", 3)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", 0, "max", 5))
            .withPosition(1, 0)
            .withSize(2, 2).getEntry();

        ballOverflowWidget = teleopTab.add("Ball overflow", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("color when false", "#7E8083", "color when true", "#8b0000"))
            .withPosition(1, 2)
            .withSize(2, 1).getEntry();
        
        matchTimeWidget = timeValueLayout.add("Match time", 150)
            .withWidget("Match time")
            .withSize(0, 0).getEntry();

        ShuffleboardLayout sensorValueLayout = timeValueLayout.getLayout("Sensor values", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 3, "number of rows", 2))
            .withSize(0, 1); // = withPosition
        encodersWidget = sensorValueLayout.add("Encoders", "404")
            .withSize(0, 0).getEntry();
        gyroWidget = sensorValueLayout.add("Gyro", "404")
            .withSize(1, 0).getEntry();
        beamBreakWidget = sensorValueLayout.add("Intake beam break", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withSize(2, 0).getEntry();
        isCamera1Widget = sensorValueLayout.add("Camera 1?", RobotMap.isFirstCamera)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withSize(0, 1).getEntry();
        isFastModeWidget = sensorValueLayout.add("Fast mode?", RobotMap.isSpeedMode)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withSize(1, 1).getEntry();
        isIntakeUpWidget = sensorValueLayout.add("Intake up?", !RobotMap.isIntakeDown)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withSize(2, 1).getEntry();

        // Setup buttons tab

        boolean[] empty = new boolean[13];
        operatorControllerWidget = buttonsTab.add("Operator Controller", empty)
            .withWidget("Xbox Controller")
            .withProperties(Map.ofEntries(  new AbstractMap.SimpleEntry<String, String>("a descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("b descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("x descr", "intake extend/retract"),
                                            new AbstractMap.SimpleEntry<String, String>("y descr", "run helix & intake back (unjam cell)"),
                                            new AbstractMap.SimpleEntry<String, String>("lb descr", "climber brake"),
                                            new AbstractMap.SimpleEntry<String, String>("rb descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("ls descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("rs descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("left joystick descr", "winch"),
                                            new AbstractMap.SimpleEntry<String, String>("right joystick descr", "intake in/out"),
                                            new AbstractMap.SimpleEntry<String, String>("left trigger descr", "extend climber"),
                                            new AbstractMap.SimpleEntry<String, String>("right trigger descr", "flywheel program"),
                                            new AbstractMap.SimpleEntry<String, String>("pov descr", "helix in/out"),
                                            
                                            new AbstractMap.SimpleEntry<String, String>("background color", "#333333"),
                                            new AbstractMap.SimpleEntry<String, String>("button color", "#666666"),
                                            new AbstractMap.SimpleEntry<String, String>("text color 1", "#f2f2f2")))
            .withPosition(0, 0)
            .withSize(4, 3).getEntry();

        driverControllerWidget = buttonsTab.add("Driver Controller", empty)
            .withWidget("Xbox Controller")
            .withProperties(Map.ofEntries(  new AbstractMap.SimpleEntry<String, String>("a descr", "intake + vision takeover"),
                                            new AbstractMap.SimpleEntry<String, String>("b descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("x descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("y descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("lb descr", "camera toggle"),
                                            new AbstractMap.SimpleEntry<String, String>("rb descr", "slow/fast toggle"),
                                            new AbstractMap.SimpleEntry<String, String>("ls descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("rs descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("left joystick descr", "make robot go brrrrr"),
                                            new AbstractMap.SimpleEntry<String, String>("right joystick descr", "make robo go wheee"),
                                            new AbstractMap.SimpleEntry<String, String>("left trigger descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("right trigger descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("pov descr", ""),
                                            
                                            new AbstractMap.SimpleEntry<String, String>("background color", "#cccccc"),
                                            new AbstractMap.SimpleEntry<String, String>("button color", "#999999")))
            .withPosition(5, 0)
            .withSize(4, 3).getEntry();
    }

    /**
     * Updates data used in Shuffleboard. This will be updated even if the robot is disabled.
     */
    public static void printDataToShuffleboard() {
        //control panel
        setSpeedWidget.setDouble(Subsystems.flyboi.wheelSpeed);
        actualSpeedWidget.setDouble(Subsystems.flyboi.getPower());

        //cell count
        cellCountWidget.setDouble(Subsystems.helix.cellCount);
        ballOverflowWidget.setBoolean(Subsystems.helix.cellCount > 5);

        //match time
        matchTimeWidget.setDouble(DriverStation.getInstance().getMatchTime() + (DriverStation.getInstance().isAutonomous() ? 135 : 0));

        //sensor values
        encodersWidget.setString(Subsystems.driveBase.getLeftPosition() + " L " + Subsystems.driveBase.getRightPosition() + " R");
        gyroWidget.setDouble(Subsystems.driveBase.getGyroAngle());
        beamBreakWidget.setBoolean(Subsystems.intake.getCellEntered());
        isFastModeWidget.setBoolean(RobotMap.isSpeedMode);
        isCamera1Widget.setBoolean(RobotMap.isFirstCamera);
        isIntakeUpWidget.setBoolean(!RobotMap.isIntakeDown);
    }

    /**
     * Updates data used in Shuffleboard. Used for data that is only useful when the robot isn't being operated, such as pre-match checks.
     */
    public static void disabledPeriodic() {
        //buttons
        boolean[] buttons = new boolean[13];
        buttons[8] = Math.abs(UserInterface.operatorController.getLeftJoystickX()) > 0.1 || Math.abs(UserInterface.operatorController.getLeftJoystickY()) > 0.1;
        buttons[9] = Math.abs(UserInterface.operatorController.getRightJoystickX()) > 0.1 || Math.abs(UserInterface.operatorController.getRightJoystickY()) > 0.1;
        operatorControllerWidget.setBooleanArray(buttons);
        buttons[8] = Math.abs(UserInterface.driverController.getLeftJoystickX()) > 0.1 || Math.abs(UserInterface.driverController.getLeftJoystickY()) > 0.1;
        buttons[9] = Math.abs(UserInterface.driverController.getRightJoystickX()) > 0.1 || Math.abs(UserInterface.driverController.getRightJoystickY()) > 0.1;
        driverControllerWidget.setBooleanArray(buttons);
    }


    // AUTONOMOUS

    /**
     * Sets the current autonomous according to the choices on Shuffleboard.
     */
    public static void setAutonomous() {
        try {
            autonomous = new Autonomous(autoChooserWidget.getSelected());
            autoSetupSuccessfulWidget.setBoolean(true);
            selectedAutoLabelWidget.setString(Autonomous.getNameFromFile(autoChooserWidget.getSelected()));
        } catch (IOException e) {
            System.out.println("AUTONOMOUS ERROR: File " + autoChooserWidget.getSelected() + " not found or other I/O error");
            try {
                autonomous = new Autonomous();
                selectedAutoLabelWidget.setString("Default: " + Autonomous.getNameFromFile(Autonomous.defaultPath));
            } catch (IOException e1) {
                selectedAutoLabelWidget.setString("DEFAULT NOT FOUND OR I/O ERROR");
                e1.printStackTrace();
            } finally {
                autoSetupSuccessfulWidget.setBoolean(false);
            }
        }
    }

    /**
     * Updates the auto files available to choose from.
     */
    private static void updateAutoFiles(SendableChooser<String> chooser) {
        chooser.setDefaultOption("Default: " + Autonomous.getNameFromFile(Autonomous.defaultPath), Autonomous.defaultPath);
        chooser.addOption("Left", "/path/to/Left.txt"); //TODO
        chooser.addOption("Right", "/path/to/Right.txt");
    }

    public static Autonomous getAutonomous() {
        return autonomous;
    }
}