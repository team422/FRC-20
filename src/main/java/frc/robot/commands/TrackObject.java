package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.RobotMap;
import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.links.*;
import java.util.ArrayList;

public class TrackObject extends Command {

    Pixy2CCC.Block biggestBlock;
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
        
       // blockX = pie.getEntry("blockX");
        // blockY = pie.getEntry("blockY");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
        //this.setInterruptible(true);
    }

    @Override
    public void execute() {

        try {
            biggestBlock = Subsystems.pixy.getBiggestBlock();
            blockX = biggestBlock.getX();
            blockWidth = biggestBlock.getWidth();
            frameWidth = Subsystems.pixy.readFrameWidth();
            counter = 0;
        } catch (java.lang.NullPointerException e) {
            if (counter < 10){
                counter ++;                
            }
            else {
                Subsystems.driveBase.stopMotors();
            }
            return;
        }


        
        
        double blockCenter = (blockWidth/2)+blockX;
        double correction = blockCenter-(frameWidth/2);
        correction *= 0.00632911d; //sets correction to -1 to 1 value        

        System.out.println("correction is" + correction);
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