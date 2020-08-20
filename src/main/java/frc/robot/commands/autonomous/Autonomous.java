package frc.robot.commands.autonomous;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
// import frc.robot.commands.*;

/**
 * Creates an autonomous from a file.
 */
public class Autonomous extends CommandGroup {

    public static final String defaultPath = "generated/ExampleTrajectory.txt";

    public double[] path = {};

    /**
     * Creates an autonomous from the file chosen in Shuffleboard.
     * 
     * @param filename The path to the file.
     * @throws IOException
     */
    public Autonomous(String filename) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().getAbsolutePath() + filename));
        //TODO: create auto from file
        f.close();
    }

    /**
     * Creates the default autonomous.
     */
    public Autonomous() throws IOException {
        this(defaultPath);
    }

    /**
     * Gets the readable name of the file from its filename, e.g. Default from path/to/Default.txt.
     */
    public static String getNameFromFile(String filename) {
        String[] input = filename.split("/");
        return input[input.length-1].split(".")[0];
    }
}