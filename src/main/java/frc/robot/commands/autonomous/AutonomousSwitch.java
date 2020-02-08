package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

public class AutonomousSwitch extends CommandGroup {

    private StartingPosition startingPosition;
    private double delay;
    private boolean pushRobot;
    private IntakeSource intakeSource;
    public String description = "";
    
    public enum StartingPosition {
        LEFT, CENTER, RIGHT
    }
    
    public enum IntakeSource {
        TRENCH, RENDEZVOUS, MIXED, NONE
    }
    
    public AutonomousSwitch(StartingPosition StartingPosition, double Delay, boolean PushRobot, IntakeSource IntakeSource) {
        this.startingPosition = StartingPosition;
        this.delay = Delay;
        this.pushRobot = PushRobot;
        this.intakeSource = IntakeSource;

        addSequential(new WaitCommand(delay));

        switch (startingPosition) {
            case LEFT:
                System.out.println("Left!");
                description += "Starts on left, ";
                break;
            case RIGHT:
                System.out.println("Right!");
                description += "Starts on right, ";
                break;
            case CENTER:
            default:
                System.out.println("Middle/Center/Median!");
                description += "Starts at center, ";
				break;
        }

        if (pushRobot && startingPosition == AutonomousSwitch.StartingPosition.CENTER) {
            //pushing robo
        } else if (pushRobot && startingPosition == AutonomousSwitch.StartingPosition.RIGHT) {

        } else if (pushRobot && startingPosition == AutonomousSwitch.StartingPosition.LEFT) {
            
        }
    }

    public boolean matchesSettings(StartingPosition StartingPosition, double Delay, boolean PushRobot, IntakeSource IntakeSource) {
        return (startingPosition == StartingPosition) && (delay == Delay) && (pushRobot == PushRobot) && (intakeSource == IntakeSource);
    }

    /**
     * Checks if this is a valid choice.
     * @param StartingPosition
     * @param IntakeSource
     * @return Whether this can create an autonomous.
     */
    public static boolean doChoicesWork(StartingPosition StartingPosition, IntakeSource IntakeSource) {
        if (StartingPosition == AutonomousSwitch.StartingPosition.LEFT && IntakeSource == AutonomousSwitch.IntakeSource.TRENCH) {
            return false;
        } else if (StartingPosition == AutonomousSwitch.StartingPosition.RIGHT && IntakeSource == AutonomousSwitch.IntakeSource.RENDEZVOUS) {
            return false;
        } else if (StartingPosition == AutonomousSwitch.StartingPosition.LEFT && IntakeSource == AutonomousSwitch.IntakeSource.MIXED) {
            return false;
        }

        return true;
    }
}