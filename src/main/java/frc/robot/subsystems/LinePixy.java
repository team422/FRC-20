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
    
    private Pixy2Line.Vector[] vectors;

    public void printVectors(){
        pixyLine.getAllFeatures();
        vectors = pixyLine.getVectors();
        try {for(int i = 0; i < vectors.length; i++) {
            for (int j = i + 1; j < vectors.length; j++){
                int Vec1X = Math.abs(vectors[i].getX0()-vectors[i].getX1());
                int Vec1Y = Math.abs(vectors[i].getY0()-vectors[i].getY1());
                int Vec2X = Math.abs(vectors[j].getX0()-vectors[j].getX1());
                int Vec2Y = Math.abs(vectors[j].getY0()-vectors[j].getY1());
                double angle1 = Math.atan(Vec1Y/(Vec1X+.01));
                double angle2 = Math.atan(Vec1Y/(Vec1X+.01));
                angle1 =angle1*180/3.141592653;
                angle2 = angle2*180/3.141592653;
                if (Math.floor((180-angle1-angle2)/10)==7){
                    System.out.println("GOOD");
                }
                
            }
        }
        System.out.println();
    }
    catch (java.lang.NullPointerException e){
        System.out.println("Double Oops");
    }
    }

	/*public void printIntersection() {
		pixyLine.getAllFeatures();
        try {
            System.out.println("intersection angle is " + pixyLine.getIntersections()[0].getLines()[0].getAngle());    
        }
        catch (java.lang.NullPointerException e){
            System.out.println("OOPS");
        }
	}*/

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