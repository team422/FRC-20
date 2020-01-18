package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.robot.commands.*;

public class TestAutonomous extends CommandGroup {

  public double timeout = 2; //replace with actual value
  public double speed = 0.3;

  public TestAutonomous() {
   System.out.println("Start auto.");
   addSequential(new DriveStraight(12, speed, true, timeout));
   System.out.println("First step complete.");
   addSequential(new Turn(180, speed, timeout));
   System.out.println("Second step complete.");
   addSequential(new DriveStraight(12, speed, true, timeout));
   System.out.println("Third step complete.");
   addSequential(new Turn(180, speed, timeout));
   System.out.println("Fourth step complete.");
  }
}