package frc.robot.commands.autonomous;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
// import frc.robot.commands.*;

/**
 * Creates an autonomous from a file.
 */
public class Autonomous extends SequentialCommandGroup {

    public static final String defaultPath = "generated/ExampleTrajectory.txt";

    /** The path of this autonomous, in inches from the top left corner. Used in the Path shuffleboard widget. */
    public double[] path = {};

    /**
     * Creates an autonomous from a file.
     * 
     * @param filename The path to the file, relative to the deploy directory.
     * @throws IOException
     */
    public Autonomous(String filename) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath() + "/" + filename));
        //TODO: create auto from file
        f.close();
    }

    /**
     * Creates the default autonomous.
     */
    public Autonomous() throws IOException {
        this(defaultPath);
    }
}