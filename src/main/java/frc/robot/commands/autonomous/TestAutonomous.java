package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.robot.commands.*;

public class TestAutonomous extends CommandGroup {

  private final double timeout = 9; //replace with actual value
  private final double speed = 0.3;

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