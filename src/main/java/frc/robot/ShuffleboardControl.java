package frc.robot;

import java.util.Map;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.autonomous.AutonomousSwitch;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class ShuffleboardControl {

    private static AutonomousSwitch autonomous;
    private static SendableChooser<AutonomousSwitch.StartingPosition> positionChooser;
    private static NetworkTableEntry delayChooser;
    private static NetworkTableEntry pushRobotChooser;
    private static SendableChooser<AutonomousSwitch.IntakeSource> intakeChooser;
    private static NetworkTableEntry autoLabel;

    private static NetworkTableEntry driverControllerWidget;
    private static NetworkTableEntry operatorControllerWidget;

    private static NetworkTableEntry cellCountWidget;
    private static NetworkTableEntry overflowWidget;

    private static NetworkTableEntry leftEncoders;
    private static NetworkTableEntry rightEncoders;
    private static NetworkTableEntry gyroWidget;
    private static NetworkTableEntry intakeBeamBreakWidget;
    private static NetworkTableEntry isSpeedModeWidget;
    private static NetworkTableEntry isCamera1Widget;
    private static NetworkTableEntry isIntakeUpWidget;

    private static NetworkTableEntry currentFlywheelWidget;
    private static NetworkTableEntry actualFlywheelWidget;


    // SHUFFLEBOARD

    /**
     * Arranges the Shuffleboard layout.
     */
    public static void layoutShuffleboard() {
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
    public static void printDataToShuffleboard() {
        //controller id's
        driverControllerWidget.setBoolean(Math.abs(UserInterface.leftJoystix.getJoystickY()) > 0.1 || Math.abs(UserInterface.rightJoystix.getJoystickY()) > 0.1);
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


    // AUTONOMOUS

    /**
     * Sets the default autonomous in case one is not chosen.
     */
    public static void setupAutonomous() {
        autonomous = new AutonomousSwitch(AutonomousSwitch.StartingPosition.CENTER, 0, false, AutonomousSwitch.IntakeSource.TRENCH); //default
    }

    /**
     * Updates the current autonomous according to the choices on Shuffleboard.
     */
    public static void updateAutonomous() {
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

    public static AutonomousSwitch getAutonomous() {
        return autonomous;
    }
}