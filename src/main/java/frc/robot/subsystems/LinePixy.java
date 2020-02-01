package frc.robot.subsystems;

import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.links.*;
import java.util.ArrayList;

/**
 * A Pixy2 camera connected to the roborio via an I2C port, setup to track lines.
 */
public class LinePixy {

    private Pixy2 pixy;
    private Pixy2Line pixyLine;
    protected int frameWidth = -1;
    protected int frameHeight = -1;

    public LinePixy() {
        pixy = Pixy2.createInstance(new I2CLink()); // Creates a new Pixy2 camera using I2C
        System.out.println(pixy.init()); // Initializes the camera and prepares to send/receive data
        pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
        pixy.setLED(0, 255, 0); // Sets the RGB LED to full white
        pixyLine = pixy.getLine(); // Sets up for line tracking
        System.out.println(pixy);
	}
	
	public void printIntersection() {
		pixyLine.getMainFeatures();
		Pixy2Line.Intersection[] intersections = pixyLine.getIntersections();
		System.out.print("this many intersections" + intersections.length);
		if (intersections.length > 0) {
			System.out.print("intersection 0 is" + intersections[0]);
			System.out.println(", intersection angle is " + intersections[0].getLines()[0].getAngle());
		} else {
			System.out.println("no intersections :(");
		}

	}

    /**
     * Sets the RGB LED color.
     * @param r Amount of red (0-255)
     * @param g Amount of green (0-255)
     * @param b Amount of blue (0-255)
     * @return Pixy2 error code
     */
    public byte setLED(int r, int g, int b) {
        return pixy.setLED(r, g, b);
    }

    /**
     * @return Width of the Pixy's field of view.
     */
    public int getFrameWidth() {
        return pixy.getFrameWidth();
	}
}