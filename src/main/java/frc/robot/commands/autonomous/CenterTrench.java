package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

/**
 * Autonomous routine starting in front of the power port & intaking from the trench run.
 * <dd>1. Shoot preloaded power cells</dd>
 * <dd>2. Drive to trench</dd>
 * <dd>3. Intake five power cells</dd>
 * <dd>4. Return to original starting position</dd>
 * <dd>5. Shoot intaken power cells</dd>
 */
public class CenterTrench extends CommandGroup {

/**
 * Autonomous routine starting in front of the power port & intaking from the trench run.
 * <dd>1. Shoot preloaded power cells</dd>
 * <dd>2. Drive to trench</dd>
 * <dd>3. Intake five power cells</dd>
 * <dd>4. Return to original starting position</dd>
 * <dd>5. Shoot intaken power cells</dd>
 */
  public CenterTrench() {
    //addSequential(new Shoot(3)); //shoot 3 
    addSequential(new Turn(-43.45, 0.3, 3)); //turn towards trench
    addSequential(new DriveStraight(97.29, 0.5, 5)); //drive to trench
    addSequential(new Turn(43.45, 0.3, 3)); //turn to face straight
    //addSequential(new intakeOn()); //intake on
    addSequential(new DriveStraight(162.27, 0.3, 5)); //drive straight until power cells are intaken
    //add Sequential(new intakeOff()); //intake off
    addSequential(new DriveStraight(-54.27, 0.5, 5));//drive out of control panel
    addSequential(new Turn(-20.53, 0.3, 3));//turn towards center
    addSequential(new DriveStraight(-190.75, 0.5, 5));//drive straight
    addSequential(new Turn(20.53, 0.3, 3));//turn towards goal
    //addSequential(new Shoot(5)); //shoot 5
  }
}