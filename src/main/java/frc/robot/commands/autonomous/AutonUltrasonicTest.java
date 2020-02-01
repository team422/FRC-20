package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.Subsystems;


public class AutonUltrasonicTest extends CommandGroup {

    public AutonUltrasonicTest() {

        double endingInches = 30;
        double currentInches = Subsystems.ultrasonic.getInchesAway();

        double inchesToMove = currentInches - endingInches;

        if (inchesToMove > 0){
            addSequential(new DriveStraight(inchesToMove,.5,5));
        }
    }
}
