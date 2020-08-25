package frc.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.autonomous.Autonomous;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

/**
 * Manages the Shuffleboard layout, creates autonomoi by passing file names to the Autonomous constructor, and records autos.
 * 
 * A utility class. Do not create an instance of this class.
 */
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

    private static boolean recordingStarted = false;
    private static boolean recordingComplete = false;
    private static File temp;
    private static final Timer timer = new Timer();
    private static final int autoLength = 15;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd_HHmmss");

    // SHUFFLEBOARD

    /**
     * Arranges the Shuffleboard layout. An initiation method; should only be called once, in Robot.robotInit().
     */
    public static void layoutShuffleboard() {
        // Get references to tabs & layouts
        ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");
        ShuffleboardTab teleopTab = Shuffleboard.getTab("Teleoperated");
        ShuffleboardTab buttonsTab = Shuffleboard.getTab("Buttons");

        ShuffleboardLayout chooseAutoLayout = autonomousTab.getLayout("Choose auto", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 1, "number of rows", 3))
            .withPosition(0, 0)
            .withSize(2, 3);
        ShuffleboardLayout recordAutoLayout = autonomousTab.getLayout("Record new auto", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 1, "number of rows", 6, "label position", "HIDDEN"))
            .withPosition(6, 0)
            .withSize(2, 3);
        ShuffleboardLayout timeValueLayout = teleopTab.getLayout("Match time & Sensor values", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 1, "number of rows", 2, "label position", "HIDDEN"))
            .withPosition(6, 1)
            .withSize(3, 2);
        ShuffleboardLayout controlsLayout = teleopTab.getLayout("Controls", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(1, 3);

        // Setup autonomous tab

        autoChooserWidget = new SendableChooser<String>();

        //set each found file as an option
        autoChooserWidget.setDefaultOption("Default: " + getNameFromFile(Autonomous.defaultPath) + " (" + Autonomous.defaultPath.split("/")[0] + " path)", Autonomous.defaultPath);
        for (File dir : Filesystem.getDeployDirectory().listFiles()) {
            if (dir.isDirectory()) {
                String[] folders = dir.getPath().split("/");
                String type = folders[folders.length-2];
                for (String filename : dir.list()) {
                    String path = type + "/" + filename;
                    autoChooserWidget.addOption(getNameFromFile(path) + " (" + type + " path)", path);
                }
            }
        }

        chooseAutoLayout.add("Browse autos", autoChooserWidget)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(0, 0); // = withPosition
        chooseAutoLayout.add("Choose selected", new SetAuto())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 1);
        selectedAutoLabelWidget = chooseAutoLayout.add("Currently selected auto:", "Default")
            .withSize(0, 2).getEntry();

        autoSetupSuccessfulWidget = autonomousTab.add("Auto setup successful", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withPosition(0, 2)
            .withSize(2, 1).getEntry();
        
        double[] emptyDouble = new double[0];
        pathWidget = autonomousTab.add("Path", emptyDouble)
            .withWidget("Path")
            .withProperties(Map.of("year", 2020, "field width", 26*12 + 11.25, "field height", 52*12 + 5.25))
            .withPosition(2, 0)
            .withSize(3, 4).getEntry();

        ShuffleboardLayout startCancelRecordingLayout = recordAutoLayout.getLayout("Start/cancel recording", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1, "label position", "HIDDEN"))
            .withSize(0, 0);
        startCancelRecordingLayout.add("Start", new StartRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 0);
        startCancelRecordingLayout.add("Cancel", new CancelRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(1, 0);
        
        countdownWidget = recordAutoLayout.add("Countdown to recording", "")
            .withSize(0, 1).getEntry();

        recordingTimerWidget = recordAutoLayout.add("Time passed since recording", 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 0, "max", autoLength, "num tick marks", 4))
            .withSize(0, 2).getEntry();
        
        recordAutoLayout.add("Replay", new ReplayRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 3);
        
        ShuffleboardLayout saveRecordingLayout = recordAutoLayout.getLayout("Save recording", BuiltInLayouts.kGrid)
            .withProperties(Map.of("number of columns", 2, "number of rows", 1))
            .withSize(0, 4);
        saveRecordingLayout.add("Save as", new SaveRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 0);
        filenameWidget = saveRecordingLayout.add("Filename", "")
            .withSize(1, 0).getEntry();

        recordAutoLayout.add("Discard", new DiscardRecording())
            .withWidget(BuiltInWidgets.kCommand)
            .withSize(0, 5);

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
            .withSize(0, 1);
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

        boolean[] emptyBool = new boolean[13];
        operatorControllerWidget = buttonsTab.add("Operator Controller", emptyBool)
            .withWidget("Xbox Controller")
            .withProperties(Map.ofEntries(  new AbstractMap.SimpleEntry<String, String>("a descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("b descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("x descr", "intake extend/retract"),
                                            new AbstractMap.SimpleEntry<String, String>("y descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("lb descr", "[climber brake]"),
                                            new AbstractMap.SimpleEntry<String, String>("rb descr", "start flywheel early"),
                                            new AbstractMap.SimpleEntry<String, String>("ls descr", "run helix & intake back (unjam cell)"),
                                            new AbstractMap.SimpleEntry<String, String>("rs descr", "reset cell count to 0"),
                                            new AbstractMap.SimpleEntry<String, String>("left joystick descr", "[winch]"),
                                            new AbstractMap.SimpleEntry<String, String>("right joystick descr", "intake in/out"),
                                            new AbstractMap.SimpleEntry<String, String>("left trigger descr", "[extend climber]"),
                                            new AbstractMap.SimpleEntry<String, String>("right trigger descr", "flywheel program"),
                                            new AbstractMap.SimpleEntry<String, String>("pov descr", "helix in/out"),
                                            
                                            new AbstractMap.SimpleEntry<String, String>("background color", "#333333"),
                                            new AbstractMap.SimpleEntry<String, String>("button color", "#666666"),
                                            new AbstractMap.SimpleEntry<String, String>("text color 1", "#f2f2f2"),
                                            new AbstractMap.SimpleEntry<String, String>("text color 2", "#cccccc")))
            .withPosition(0, 0)
            .withSize(4, 3).getEntry();

        driverControllerWidget = buttonsTab.add("Driver Controller", emptyBool)
            .withWidget("Xbox Controller")
            .withProperties(Map.ofEntries(  new AbstractMap.SimpleEntry<String, String>("a descr", "[intake + vision takeover]"),
                                            new AbstractMap.SimpleEntry<String, String>("b descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("x descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("y descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("lb descr", "camera toggle"),
                                            new AbstractMap.SimpleEntry<String, String>("rb descr", "slow/fast toggle"),
                                            new AbstractMap.SimpleEntry<String, String>("ls descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("rs descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("left joystick descr", "rotation"),
                                            new AbstractMap.SimpleEntry<String, String>("right joystick descr", "velocity"),
                                            new AbstractMap.SimpleEntry<String, String>("left trigger descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("right trigger descr", ""),
                                            new AbstractMap.SimpleEntry<String, String>("pov descr", ""),
                                            
                                            new AbstractMap.SimpleEntry<String, String>("background color", "#cccccc"),
                                            new AbstractMap.SimpleEntry<String, String>("button color", "#999999")))
            .withPosition(5, 0)
            .withSize(4, 3).getEntry();


        //Setup file system for recording
        File recentDir = new File(Filesystem.getDeployDirectory().getAbsolutePath() + "/recently recorded");
        recentDir.mkdir();

        //delete recently created files
        //TODO: check if the "recently created" folder is automatically deleted on deploy; if so, don't bother with this
        File files[] = recentDir.listFiles();
        for (File file : files) {
            file.delete();
        }
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
     * Updates data used in Shuffleboard. Used for data that is only useful when the robot isn't being operated, such as pre-match checks or auto refreshing.
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
     * Sets the current autonomous to the specified file.
     */
    public static void setAutonomous(String filename) {
        try {
            autonomous = new Autonomous(filename);
            pathWidget.setDoubleArray(autonomous.path);
            autoSetupSuccessfulWidget.setBoolean(true);
            selectedAutoLabelWidget.setString(getNameFromFile(filename));
        } catch (IOException e) {
            selectedAutoLabelWidget.setString("File " + filename + " not found or other I/O error");
            autoSetupSuccessfulWidget.setBoolean(false);
            e.printStackTrace();
            try {
                autonomous = new Autonomous();
            } catch (IOException e1) {
                selectedAutoLabelWidget.setString("Neither selected file " + filename + " nor default file " + Autonomous.defaultPath + " found or other I/O error");
                e1.printStackTrace();
            }
        }
    }

    /**
     * Sets the current autonomous to the choice selected in Shuffleboard.
     */
    public static void setAutonomous() {
        setAutonomous(autoChooserWidget.getSelected());
    }

    public static Autonomous getAutonomous() {
        return autonomous;
    }


    // RECORDING
    // [not started & not complete] -> [started but not complete] -> [complete] -> [not started & not complete]

    private static void startRecording() {
        if (!recordingStarted && !recordingComplete) {
            timer.reset();
            timer.start();
            recordingStarted = true;
            try { temp = File.createTempFile("rec", "txt", new File(Filesystem.getDeployDirectory().getAbsolutePath() + "/recently recorded")); }
            catch (IOException e) {
                timer.stop();
                timer.reset();
                countdownWidget.setString("File could not be created. Fetch programming.");
                e.printStackTrace();
            }
        } else {
            countdownWidget.setString("You cannot start a new recording until you have saved or discarded your last one.");
        }
    }

    private static void cancelRecording() {
        if (recordingStarted && !recordingComplete) {
            timer.stop();
            timer.reset();
            countdownWidget.setString("");
            recordingTimerWidget.setDouble(0);
            recordingStarted = false;
        } else {
            countdownWidget.setString(recordingComplete ? "It is too late to cancel this recording. Save or discard it instead." : "There is no recording in progress to cancel.");
        }
    }

    private static void replayRecording() {
        if (recordingComplete) {
            setAutonomous("recently recorded/" + temp.getName());
        } else {
            countdownWidget.setString("There is no completed recording to play back.");
        }
    }

    private static void saveRecording() {
        if (recordingComplete) {
            File target = new File(Filesystem.getDeployDirectory() + "/recently recorded/" + filenameWidget.getString("") + ".txt");
            if (target.exists() || new File(Filesystem.getDeployDirectory() + "/recorded/" + filenameWidget.getString("") + ".txt").exists()) {
                countdownWidget.setString("A recorded auto with this name already exists.");
            } else if (filenameWidget.getString("") == "") {
                countdownWidget.setString("You must specify a filename to save as.");
            } else {
                try {
                    Files.copy(temp.toPath(), target.toPath());
                    temp.delete();
                    autoChooserWidget.addOption(filenameWidget.getString("") + " (recorded path)", "recently recorded/" + filenameWidget.getString("") + ".txt");
                } catch (IOException e) {
                    countdownWidget.setString("I/O error occurred trying to save file. Fetch programming.");
                    e.printStackTrace();
                }
            }
        } else {
            countdownWidget.setString("There is no completed recording to save.");
        }
    }

    private static void discardRecording() {
        if (recordingComplete) {
            temp.delete();

            timer.reset();
            countdownWidget.setString("");
            recordingTimerWidget.setDouble(0);
            recordingStarted = false;
        } else {
            countdownWidget.setString("There is no completed recording to discard.");
        }
    }

    /**
     * Returns true if Shuffleboard is in the process of recording a new auto.
     */
    public static boolean isRecording() {
        return recordingStarted && !recordingComplete;
    }

    /**
     * Called when Shuffleboard is in the process of recording a new auto in teleop.
     */
    public static void recordingPeriodic() {
        double time = timer.get();
        if (time > autoLength + 3) {
            timer.stop();
            recordingTimerWidget.setDouble(autoLength);
            filenameWidget.setString(formatter.format(new Date()));
            recordingComplete = true;
        } else if (time >= 3) {
            recordingTimerWidget.setDouble(time - 3);

            //TODO: record in file
        } else {
            recordingTimerWidget.setDouble(0);
        }

        if (time < 1) {
            countdownWidget.setString("3...");
        } else if (time < 2) {
            countdownWidget.setString("3... 2...");
        } else if (time < 3) {
            countdownWidget.setString("3... 2... 1...");
        } else if (time < 3.2) {
            countdownWidget.setString("3... 2... 1... GO");
        }
    }



    // COMMANDS - Redirect to methods

    private static class CancelRecording extends Command {
        public CancelRecording() { super("Cancel"); }
        @Override
        protected void execute() { cancelRecording(); }
        @Override
        protected boolean isFinished() { return true; }
    }

    private static class DiscardRecording extends Command {
        public DiscardRecording() { super("Discard"); }
        @Override
        protected void execute() { discardRecording(); }
        @Override
        protected boolean isFinished() { return true; }
    }

    private static class ReplayRecording extends Command {
        public ReplayRecording() { super("Replay"); }
        @Override
        protected void execute() { replayRecording(); }
        @Override
        protected boolean isFinished() { return true; }
    }

    private static class SaveRecording extends Command {
        public SaveRecording() { super("Save"); }
        @Override
        protected void execute() { saveRecording(); }
        @Override
        protected boolean isFinished() { return true; }
    }

    private static class SetAuto extends Command {
        public SetAuto() { super("Select"); }
        @Override
        protected void execute() { setAutonomous(); }
        @Override
        protected boolean isFinished() { return true; }
    }

    private static class StartRecording extends Command {
        public StartRecording() { super("Start"); }
        @Override
        protected void execute() { startRecording(); }
        @Override
        protected boolean isFinished() { return true; }
    }


    // HELPER METHODS

    /**
     * Gets the readable name of the file from its filename, e.g. Default from path/to/Default.txt.
     */
    private static String getNameFromFile(String filename) {
        String[] input = filename.split("/");
        return input[input.length-1].split(".")[0];
    }
}