package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

/**
 * Creates an autonomous according to the choices chosen in Shuffleboard.
 */
public class AutonomousSwitch extends CommandGroup {

    public final StartingPosition startingPosition;
    public final double delay;
    public final boolean pushRobot;
    public final IntakeSource intakeSource;
    public String description = "";
    public final boolean visionEnabled;

    public final double robotLength = 38.75; //in inches

    public enum StartingPosition {
        LEFT, CENTER, RIGHT
    }

    public enum IntakeSource {
        TRENCH, RENDEZVOUS, MIXED
    }

    /**
     * Creates an autonomous according to the choices chosen in Shuffleboard.
     * @param StartingPosition Where the robot starts on the field (right of power port, left of it, or directly in front of it).
     * @param Delay How long the robot should delay starting its automous, in seconds.
     * @param PushRobot Whether our robot will push another robot across the initiation line.
     * @param IntakeSource Where we intake power cells from after shooting (trench, rendezvous, or a mix of the two).
     * @param VisionEnabled Whether or not our robot will use vision/sensing in its autonomous routine. If false, this will use dead reckoning.
     */
    public AutonomousSwitch(StartingPosition StartingPosition, double Delay, boolean PushRobot, IntakeSource IntakeSource, boolean VisionEnabled) {
        this.startingPosition = StartingPosition;
        this.delay = Delay;
        this.pushRobot = PushRobot;
        this.intakeSource = IntakeSource;
        this.visionEnabled = VisionEnabled;

        addSequential(new WaitCommand(delay)); //wait for specified delay
        description = "shoots after a delay of " + Math.round(delay*100.0)/100.0 + ", "; //rounding!!!!!!!

        if (pushRobot) {
            description += "pushes robot, ";
            addSequential(new DriveStraight(-2, 0.5, 0.5)); //push robot off line
            addSequential(new DriveStraight(2, 0.5, 0.5)); //go back to original position
        } else {
            description += "doesn't push robot, ";
        }

        // please test all actual values
        // also used wait commands in place of helixing

        if (startingPosition == AutonomousSwitch.StartingPosition.CENTER) {
            description = "Starts at center, " + description;

            addSequential(new StartStopFlywheel()); //get flywheel up to speed
            addSequential(new DriveStraight(120 - robotLength + 6, 0.5, 8)); //drive to goal, temporarily shorter
            addSequential(new CellStopRetract());
            addSequential(new HelixOn()); //shoot 3 cells
            addSequential(new DriveStraight(24, 0.2, 2));
            addSequential(new HelixOff()); //stop shooting
            addSequential(new StartStopFlywheel());

            if (IntakeSource == AutonomousSwitch.IntakeSource.TRENCH) {
                description += "intakes from trench.";

                addSequential(new DriveStraight((pushRobot ? -120 + (1.5*robotLength): -120), 0.5, 8)); //back away from goal
                addSequential(new Turn(-Math.toDegrees(Math.atan(66.91/(86.63-(robotLength/2)))), 0.3, 8)); //turn left towards trench
                addSequential(new DriveStraight(-Math.sqrt(Math.pow(66.91, 2) + Math.pow(86.63 - (robotLength/2), 2)), 0.5, 8)); //go towards trench until aligned
                addSequential(new Turn(-180 + Math.toDegrees(Math.atan(66.91/(86.63-(robotLength/2)))), 0.3, 8)); //turn (same amount) right towards trench
                addSequential(new IntakeExtend()); //turn intake down & on
                addSequential(new IntakeIn());
                addSequential(new DriveStraight((pushRobot ? 180 + (1.5*robotLength) : 180), 0.3, 8)); //go to intake 5 cells from whole trench run
                addSequential(new IntakeOff()); //turn intake off & up
                addSequential(new IntakeRetract());
                addSequential(new DriveStraight(-180, 0.5, 8)); //end closer to power port

            } else if (IntakeSource == AutonomousSwitch.IntakeSource.RENDEZVOUS) {
                description += "intakes from rendezvous.";

                // addSequential(new DriveStraight(12, 0.5, 8)); //back away from goal
                // addSequential(new Turn(-45, 0.3, 8)); //turn left away from rendezvous
                // addSequential(new DriveStraight(12, 0.5, 8)); //go straight
                // addSequential(new Turn(90, 0.3, 8)); //turn towards first 2 cells
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOn()); //turn intake down & on
                // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake those 2 cells & cross 2x4
                // addSequential(new Turn(90, 0.3, 8)); //turn towards 3 remaining cells
                // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake further cells
                // addSequential(new Turn(90, 0.3, 8)); //turn towards closer cells
                // addSequential(new DriveStraight(6, 0.5, 8)); //go to intake last of 3 cells
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOff()); //turn intake off & up
                // addSequential(new Turn(135, 0.3, 8)); //turn so shooter faces towards power port
                // addSequential(new DriveStraight(12, 0.5, 8)); //end across 2x4, closer to power port

            } else if (IntakeSource == AutonomousSwitch.IntakeSource.MIXED) {
                description += "intakes 3 from trench + 2 from rendezvous.";

                addSequential(new DriveStraight((pushRobot ? -120 + (1.5*robotLength) : -120), 0.5, 8)); //back away from goal
                addSequential(new Turn(-Math.toDegrees(Math.atan(66.91/(86.63-(robotLength/2)))), 0.3, 8)); //turn left towards trench
                addSequential(new DriveStraight(Math.sqrt(Math.pow(66.91, 2) + Math.pow(86.63 - (robotLength/2), 2)), 0.5, 8)); //go towards trench until aligned
                addSequential(new Turn(-180 + Math.toDegrees(Math.atan(66.91/(86.63-(robotLength/2)))), 0.3, 8)); //turn (same amount) right towards trench
                addSequential(new IntakeExtend()); //turn intake down & on
                addSequential(new IntakeIn());
                addSequential(new DriveStraight((pushRobot ? 116 + (1.5*robotLength) : 116), 0.4, 8)); //go to intake first 3 cells from trench

                // addSequential(new Turn(90, 0.3, 8)); //turn right towards remaining rendezvous cells
                // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake 2 final cells
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOff()); //turn intake off & up
                // addSequential(new DriveStraight(-6, 0.5, 8)); //back up a lil
                // addSequential(new Turn(90, 0.3, 8)); //end turning back towards power port

            }
        } else if (startingPosition == AutonomousSwitch.StartingPosition.RIGHT) {
            description = "Starts on right, " + description;

            addSequential(new DriveStraight(10, 0.4, 8)); //just cross line

            // addSequential(new DriveStraight(-12, 0.5, 8)); //drive to goal
            // addSequential(new WaitCommand(1)); //addSequential(new Shoot()) //shoot 3 cells
            // addSequential(new DriveStraight(12, 0.5, 8)); //back away from goal

            if (IntakeSource == AutonomousSwitch.IntakeSource.TRENCH) {
                description += "intakes from trench.";
                
                // addSequential(new Turn(45, 0.3, 8)); //turn right towards trench
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOn()); //turn intake down & on
                // addSequential(new DriveStraight(24, 0.5, 8)); //go to intake 5 cells from whole trench run
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOff()); //turn intake off & up
                // addSequential(new DriveStraight(-24, 0.5, 8)); //end closer to power port

            } else { // MIXED
                description += "intakes 3 from trench + 2 from rendezvous.";

                // addSequential(new Turn(45, 0.3, 8)); //turn right towards trench
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOn()); //turn intake down & on
                // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake first 3 cells from trench
                // addSequential(new Turn(90, 0.3, 8)); //turn right towards remaining rendezvous cells
                // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake 2 final cells
                // addSequential(new WaitCommand(1)); //addSequential(new IntakeOff()); //turn intake off & up
                // addSequential(new DriveStraight(-6, 0.5, 8)); //back up a lil
                // addSequential(new Turn(90, 0.3, 8)); //end turning back towards power port

            }
        } else if (startingPosition == AutonomousSwitch.StartingPosition.LEFT) {
            description = "Starts on left, " + description;

            addSequential(new DriveStraight(10, 0.4, 8)); //just cross line

            // addSequential(new DriveStraight(-12, 0.5, 8)); //drive to goal
            // addSequential(new WaitCommand(1)); //addSequential(new Shoot()) //shoot 3 cells
            // addSequential(new DriveStraight(12, 0.5, 8)); //back away from goal

            //Must be intaking from rendezvous
            description += "intakes from rendezvous.";

            // addSequential(new Turn(-45, 0.3, 8)); //turn left towards rendezvous
            // addSequential(new WaitCommand(1)); //addSequential(new IntakeOn()); //turn intake down & on
            // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake first 2 cells
            // addSequential(new DriveStraight(-6, 0.5, 8)); //back up
            // addSequential(new Turn(45, 0.3, 8)); //turn towards last cell
            // addSequential(new DriveStraight(12, 0.5, 8)); //go to intake last of 3 cells
            // addSequential(new Turn(-135, 0.3, 8)); //turn towards other 2
            // addSequential(new DriveStraight(24, 0.5, 8)); //go to intake last 2
            // addSequential(new WaitCommand(1)); //addSequential(new IntakeOff()); //turn intake off & up
            // addSequential(new Turn(90, 0.3, 8)); //turn so shooter faces towards power port

        }
    }


    /**
     * @param StartingPosition
     * @param Delay
     * @param PushRobot
     * @param IntakeSource
     * @return Whether these inputs would create the same autonomous as currently exists (i.e. all settings match exactly).
     */
    public boolean matchesSettings(StartingPosition StartingPosition, double Delay, boolean PushRobot, IntakeSource IntakeSource, boolean VisionEnabled) {
        return (startingPosition == StartingPosition) && (delay == Delay) && (pushRobot == PushRobot) && (intakeSource == IntakeSource) && (visionEnabled == VisionEnabled);
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