package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
// import frc.robot.userinterface.UserInterface;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
// import frc.robot.RobotMap;

public class TrackObject extends Command {

    private NetworkTableEntry blockX;
    // private NetworkTableEntry blockY;
    // private double oldX;
    // private double oldY;

    public TrackObject() {
        super("TrackObject");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pie = inst.getTable("pie");
        blockX = pie.getEntry("blockX");
        // blockY = pie.getEntry("blockY");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
        //this.setInterruptible(true);
    }

    @Override
    public void execute() {
        double correction = (160.0d - blockX.getDouble(-404)) / 160.0d;
        correction *= 0.17d;
        correction += 1d;

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