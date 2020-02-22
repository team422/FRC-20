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
//import io.github.pseudoresonance.pixy2api.*;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;

//import java.util.Map;

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

    private NetworkTableEntry blockX;

    private int cellCount = 3; //replace with var from helix

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

        //camera setup
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        switchedCamera = CameraServer.getInstance().addSwitchedCamera("Camera feeds");
        switchedCamera.setSource(camera1);

        //drive settings
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        RobotMap.setSpeedAndRotationCaps(0.3, 0.5);

        autonomous = new AutonomousSwitch(AutonomousSwitch.StartingPosition.CENTER, 0, false, AutonomousSwitch.IntakeSource.TRENCH); //default
        //setup Shuffleboard interface
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();

        switchedCamera.setSource(camera1);

        //Driver controls
        //LJoy: Velocity
        //RJoy: Rotation
        //POV
        //A: Intake + vision takeover
        //B
        //X
        //Y
        UserInterface.driverController.LB.whenPressed(new SwitchCameras(switchedCamera, camera1, camera2)); //LBump: Toggle cameras
        UserInterface.driverController.A.whenPressed(new SwitchGears()); //RBump: Toggle slow/fast mode
        //LTrig
        //RTrig
        //LSmall
        //RSmall

        //Operator controls
        //LJoy: Intake cells in/out
        //RJoy: Helix move forwards/backwards
        //UserInterface.operatorController.LJ.whenPressed(new IntakeIn()); //LJoy: Intake cells in/out
        //UserInterface.operatorController.A.whenPressed(new IntakeExtendRetract()); //A: Intake extend/retract
        //UserInterface.operatorController.X.whenPressed(new StartStopFlywheel()); //X: Flywheel on/off        UserInterface.operatorController.B.whenPressed(new ToggleHelix()); //X: Flywheel on/off
        //UserInterface.operatorController.Y.whenPressed(new CellStopExtendRetract()); //X: Flywheel on/off
        //UserInterface.operatorController.Y.whenPressed(new CellStopExtend()); //X: Flywheel on/off
        //UserInterface.operatorController.LB.whenPressed(new ToggleClimberBrake()); //LBump: Toggle climber brake
        //UserInterface.operatorController.RB.whenPressed(new ExtendClimber()); //RBump: Extend climber
        //LTrig
        UserInterface.operatorController.RS.whileHeld(new FlywheelShoot());//RTrigger: starts the fly shoot command
        UserInterface.operatorController.LS.whenReleased(new FlywheelShootStop());
        //RTrig: Fly wheel program
        
        
        //LSmall 
        //RSmall

        Subsystems.intake.intakeExtend();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        //Intake cells in/out
        if (UserInterface.operatorController.getRightJoystickY() >= 0.4) {
            System.out.println("RightJoystickUp");
            Subsystems.intake.setIntakeMotors(0.8);
            if (!RobotMap.isIntakeDown) {
                System.out.println("down");
                Subsystems.intake.intakeExtend();
                RobotMap.isIntakeDown = true;
            }
        } else if (UserInterface.operatorController.getRightJoystickY() <= -0.4) {
            System.out.println("RightJoystickDown");
            Subsystems.intake.setIntakeMotors(-0.8);
            if (RobotMap.isIntakeDown) {
                Subsystems.intake.intakeRetract();
			    RobotMap.isIntakeDown = false;
            }
        } else {
            Subsystems.intake.stopIntakeMotors();
            if (RobotMap.isIntakeDown) {
                Subsystems.intake.intakeRetract();
			    RobotMap.isIntakeDown = false;
            }
        }

        //moves helix in/out 
        if (UserInterface.operatorController.getRightJoystickY() >= 0.4){
            Subsystems.helix.setHelixMotors(0.8);
        } else if (UserInterface.operatorController.getPOVAngle() == 180) {
            Subsystems.helix.setHelixMotors(-0.8);
        } else {
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
}