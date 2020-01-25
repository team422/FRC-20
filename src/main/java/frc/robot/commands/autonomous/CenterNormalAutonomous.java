package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.robot.commands.*;

public class CenterNormalAutonomous extends CommandGroup {

  public CenterNormalAutonomous() {
   //addSequential(new DriveStraight(-84, 0.3, 6)); //go to wall and shooter is the back
   //shoot here 
   addSequential(new DriveStraight(138, 0.3, 9)); //back up from wall
   addSequential(new Turn(-90, 0.3, 4));
   //addSequential(new DriveStraight(66.91, 0.5, 5));
   addSequential(new Turn(90, 0.3, 4));
   //start intake addSequential (new IntakeIn);
   addSequential(new DriveStraight(155, 0.3, 11)); //drive forward between 127.72 and 201.4 in for intake
   //stop intake addSequential (new IntakeStop);
  }
}
