package frc.robot.subsystems;

import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.links.*;
import java.util.ArrayList;



import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Pixy {

    private Pixy2 pixy;
    private Pixy2CCC pixyCCC;  
	protected int frameWidth = -1;
	protected int frameHeight = -1;

	public void initialize() {
        pixy = Pixy2.createInstance(new I2CLink()); // Creates a new Pixy2 camera using I2C
        System.out.println("Dimensions "+pixy.getFrameHeight()+" x "+pixy.getFrameWidth());
        System.out.println(pixy.init()); // Initializes the camera and prepares to send/receive data
        System.out.println("Dimensions "+pixy.getFrameHeight()+" x "+pixy.getFrameWidth());
		pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
        pixy.setLED(0, 255, 0); // Sets the RGB LED to full white
        pixyCCC = pixy.getCCC();
        System.out.println(pixy);
		System.out.println("Hello There");
	}
	 
	public byte setLED(int r, int g, int b) {
		return pixy.setLED(r, g, b);
	}

	public int readFrameWidth() {
		return pixy.getFrameWidth();
	}
	
	public Pixy2CCC.Block getBiggestBlock() {
		// Gets the number of "blocks", identified targets, that match signature 1 on the Pixy2,
		// does not wait for new data if none is available,
        // and limits the number of returned blocks to 25, for a slight increase in efficiency
        int blockCount = 0;
        try {
            
            blockCount = pixyCCC.getBlocks(false, Pixy2CCC.CCC_SIG_ALL, 25);
            
            
        } catch (java.lang.NullPointerException e) {
            System.out.println("oops):");
            return null;
        }
		// System.out.println("Found " + blockCount + " blocks!"); // Reports number of blocks found
		if (blockCount <= 0) {
			return null; // If blocks were not found, stop processing
		}
		ArrayList<Pixy2CCC.Block> blocks = pixy.getCCC().getBlocks(); // Gets a list of all blocks found by the Pixy2
		Pixy2CCC.Block largestBlock = null;
		for (Pixy2CCC.Block block : blocks) { // Loops through all blocks and finds the widest one
			if (largestBlock == null) {
				largestBlock = block;
			} else if (block.getWidth() > largestBlock.getWidth()) {
				largestBlock = block;
			}
		}
		return largestBlock;
	}
}