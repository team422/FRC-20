package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
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
    //private NetworkTableEntry blockX;
    // private NetworkTableEntry blockY;
    // private double oldX;
    // private double oldY;

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
        } catch (java.lang.NullPointerException e) {
            
        }


        double correction = (blockX-180)/130;
        correction *= 0.17d;
        

        System.out.println(correction);
        if (Math.abs(correction) > 0.2) {
            Subsystems.driveBase.setMotors(0.25*correction, -0.25*correction);
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