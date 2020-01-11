package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

public class CenterNormalAutonomous extends CommandGroup {

  public final double robotX = 34; //robotX is full width of robot including bumpers (left to right)
  public final double robotY = 38.25;  //robotY is full length of robot including bumpers (front to back)
  public double crossLine = 12; //distance to cross line (should be no more than 82.625+robotY)
  public double distanceToGoalX = 24; //replace with actual value
  public double distanceToGoalY = 36; //replace with actual value
  public double timeout = 8; //replace with actual value
  public double speed = 0.20;

  public CenterNormalAutonomous() {
    if (crossLine > 82.625+robotY) {
      //((field length minus 2*(sector depth) minus (trench run depth))/2) -4 (amount by which dimensions can vary)
      //to prevent us from going too far back
      addSequential(new DriveStraight(robotY, speed, true, timeout)); //DO NOT CHANGE
    } else {
      addSequential(new DriveStraight(crossLine, speed, true, timeout));
      addSequential(new Turn(-90, speed*1.5, timeout));
      addSequential(new DriveStraight(distanceToGoalX, speed, true, timeout));
      addSequential(new Turn(-90, speed*1.5, timeout));   
      addSequential(new DriveStraight(distanceToGoalY, speed, true, timeout));
      //addSequential(new Shoot()); //shoot
      //addSequential(new DriveStraight()); //go to intake more balls
    }
  }
}