package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousSwitch extends CommandGroup {
    
    public AutonomousSwitch(String startingPosition) {
        switch (startingPosition) {
            case "L":
                System.out.println("Left!");
                break;
            case "R":
                System.out.println("Right!");
                break;
            case "C":
            default:
                System.out.println("Middle/Center/Median!");
				break;
        }
    }
}