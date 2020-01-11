package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.CenterNormalAutonomous;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    private CenterNormalAutonomous centerNormalAutonomous;

    public Robot() {
        super(0.06);
    }

    public void robotInit() {
        System.out.println("Initializing " + RobotMap.botName + "\n");
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);

        centerNormalAutonomous = new CenterNormalAutonomous();
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        printDataToSmartDashboard();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        Scheduler.getInstance().removeAll();
        centerNormalAutonomous.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        Scheduler.getInstance().removeAll();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    private void printDataToSmartDashboard() {}
}