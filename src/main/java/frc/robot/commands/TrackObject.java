package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import io.github.pseudoresonance.pixy2api.*;

/**
 * Uses pixy to face towards a power cell and prepare to consume.
 */
public class TrackObject extends Command {

    private Pixy2CCC.Block biggestBlock;
    private double blockX;
    private double blockWidth;
    private int frameWidth;
    private int counter;

    public TrackObject() {
        super("TrackObject");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();

        frameWidth = Subsystems.pixy.getFrameWidth();
    }

    @Override
    public void execute() {
        // Attempts to get biggest block
        try {
            biggestBlock = Subsystems.pixy.getBiggestBlock();
            blockX = biggestBlock.getX();
            blockWidth = biggestBlock.getWidth();
            counter = 0;
        } catch (java.lang.NullPointerException e) {
            //if no block found for 10 loops, stop motors
            if (counter < 10) {
                counter++;
            } else {
                Subsystems.driveBase.stopMotors();
            }
            return;
        }

        double blockCenter = (blockWidth/2)+blockX;
        double correction = blockCenter-(frameWidth/2);
        correction *= 0.00632911; //shrinks correction to a -1 to 1 value (calculated with width of 316)

        if (Math.abs(correction) > 0.25) {
            Subsystems.driveBase.setMotors(-0.25*correction, 0.25*correction);
            Subsystems.pixy.setLED(255,255,0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0, 0);
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0, 0);
    }
}