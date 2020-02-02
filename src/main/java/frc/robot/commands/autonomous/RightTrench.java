package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

/**
 * Autonomous routine starting aligned with the center of the trench run & intaking from the trench run.
 * <dd>1. Shoot balls at wall</dd>
 * <dd>2. Drive to trench</dd>
 * <dd>3. Intake five power cells</dd>
 * <dd>4. Return to original starting position</dd>
 * <dd>5. Shoot intaken power cells at wall</dd>
 */
public class RightTrench extends CommandGroup {

    /**
     * Autonomous routine starting aligned with the center of the trench run & intaking from the trench run.
     */
    public RightTrench() {
        // 1. shoot balls at wall
        //go to wall
        //addSequential(new Shoot(3)); //shoot 3
        //go back to line
        // 2. go to trench
        addSequential(new Turn(25.75, 0.3, 3)); //turn towards trench
        // 3. intake power cells
        //addSequential(new intakeOn()); //intake on
        addSequential(new DriveStraight(70.63, 0.5, 5)); //drive to trench
        addSequential(new DriveStraight(162.27, 0.3, 5)); //drive straight until power cells are intaken
        //add Sequential(new intakeOff()); //intake off
        //if no(?) balls intaken, try getting other ones
        // 4. go to line
        addSequential(new DriveStraight(-232.9, 0.5, 5));//drive out of control panel
        addSequential(new Turn(-25.75, 0.3, 3));//turn towards goal
        // 5. shoot power cells
        //addSequential(new Shoot(5)); //shoot 5
    }
}
