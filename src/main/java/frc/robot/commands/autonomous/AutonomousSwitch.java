package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

public class AutonomousSwitch extends CommandGroup {

    private String startingPosition;
    private double delay;
    private boolean pushRobot;
    private String intakeSource;
    public String description = "";
    
    public AutonomousSwitch(String StartingPosition, double Delay, boolean PushRobot, String IntakeSource) {
        this.startingPosition = StartingPosition;
        this.delay = Delay;
        this.pushRobot = PushRobot;
        this.intakeSource = IntakeSource;

        switch (startingPosition) {
            case "L":
                System.out.println("Left!");
                description += "Starts on left, ";
                break;
            case "R":
                System.out.println("Right!");
                description += "Starts on right, ";
                break;
            case "C":
            default:
                System.out.println("Middle/Center/Median!");
                description += "Starts at center, ";
				break;
        }
    }

    public boolean matchesSettings(String StartingPosition, double Delay, boolean PushRobot, String IntakeSource) {
        return startingPosition.equals(StartingPosition) && (delay == Delay) && (pushRobot == PushRobot) && intakeSource.equals(IntakeSource);
    }
}