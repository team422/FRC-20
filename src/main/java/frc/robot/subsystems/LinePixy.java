package frc.robot.subsystems;

import io.github.pseudoresonance.pixy2api.*;
// import com.fasterxml.jackson.databind.util.EnumValues;

/**
 * A Pixy2 camera connected to the roborio via an I2C port, setup to track lines.
 */
public class LinePixy {

    private Pixy2 pixy;
    private Pixy2Line pixyLine;

    public LinePixy() {
        pixy = Pixy2.createInstance(new PortOptionI2CLink(0x55)); // Creates a new Pixy2 camera using I2C
        System.out.println("Setting up Line Pixy...");
        System.out.println(pixy.init()); // Initializes the camera and prepares to send/receive data
        pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
        pixy.setLED(0, 255, 0); // Sets the RGB LED to full white
        pixyLine = pixy.getLine(); // Sets up for line tracking
        System.out.println(pixy);
	}

    private Pixy2Line.Vector[] vectors;
    //declare slopes of tape lines
    double VecLeftM;
    double VecRightM;

    public void printIntersection() {
        pixyLine.getAllFeatures();
        vectors = pixyLine.getVectors();
        try {
            for(int i = 0; i < vectors.length; i++) {
                for (int j = i + 1; j < vectors.length; j++) {
                    int Vec1X0 = vectors[i].getX0();
                    int Vec1X1 = vectors[i].getX1();
                    int Vec1Y0 = vectors[i].getY0();
                    int Vec1Y1 = vectors[i].getY1();
                    int Vec2X0 = vectors[j].getX0();
                    int Vec2X1 = vectors[j].getX1();
                    int Vec2Y0 = vectors[j].getY0();
                    int Vec2Y1 = vectors[j].getY1();

                    int Vec1X = Math.abs(Vec1X0-Vec1X1);
                    int Vec1Y = Math.abs(Vec1Y0-Vec1Y1);
                    int Vec2X = Math.abs(Vec2X0-Vec2X1);
                    int Vec2Y = Math.abs(Vec2Y0-Vec2Y1);
                    double angle1 = Math.atan(Vec1Y/(Vec1X+.01));
                    double angle2 = Math.atan(Vec2Y/(Vec2X+.01));
                    angle1 = angle1*180/3.141592653;
                    angle2 = angle2*180/ 3.141592653;

                    double interceptAngle = 180-angle1-angle2;
                    //if angle of intercept is within 5 degrees of 77, the actual angle, find intersect coordinates
                    if (interceptAngle >= 72 && interceptAngle <= 82) {
                        double Vec1M;
                        double Vec2M;
                        double Vec1B;
                        double Vec2B;
                        double intersectX;
                        double intersectY;

                        //if Vector 1 is a vertical line
                        if (Vec1X0 == Vec1X1) {
                            Vec2M = (Vec2Y0 - Vec2Y1) / (Vec2X0 - Vec2X1);
                            Vec2B = -(Vec2M) * Vec2X0 + Vec2Y0;
                            intersectX = Vec1X0;
                            intersectY = Vec2M * intersectX + Vec2B;
                            Vec1M = 1000;
                        }

                        //if Vector 2 is a vertical line
                        else if (Vec2X0 == Vec2X1) {
                            Vec1M = (Vec1Y0 - Vec1Y1) / (Vec1X0 - Vec1X1);
                            Vec1B = -(Vec1M) * Vec1X0 + Vec1Y0;
                            intersectX = Vec2X0;
                            intersectY = Vec1M * intersectX + Vec1B;
                            Vec2M = 1000;
                        }

                        //if neither vector is a vertical line
                        else {
                            Vec1M = (Vec1Y0 - Vec1Y1) / (Vec1X0 - Vec1X1);
                            Vec2M = (Vec2Y0 - Vec2Y1) / (Vec2X0 - Vec2X1);
                            Vec1B = -(Vec1M)* Vec1X0 + Vec1Y0;
                            Vec2B = -(Vec2M) * Vec2X0 + Vec2Y0;
                            intersectX = (Vec2B-Vec1B) / (Vec2M-Vec1M);
                            intersectY = Vec1M * intersectX + Vec1B;
                        }

                        //double VecCos1 = ((Vec1X0)^2+(Vec1X1)^2)

                        if (Vec1M > 0) {
                            //Vec1 is positive and Vec2 is negative
                            if (Vec2M < 0) {
                                VecLeftM = Vec2M;
                                VecRightM = Vec1M;
                            }
                            //both are positive slopes
                            else if (Vec2M > 0) {
                                //Vec2 is steeper
                                if (Vec1M < Vec2M) {
                                    VecLeftM = Vec2M;
                                    VecRightM = Vec1M;
                                }
                                //Vec1 is steeper
                                else if (Vec1M > Vec2M) {
                                    VecLeftM = Vec1M;
                                    VecRightM = Vec2M;
                                }
                            }
                            //Vec1 is positive and Vec2 is a straight horizontal line
                            else if (Vec2M == 0) {
                                VecLeftM = Vec1M;
                                VecRightM = Vec2M;
                            }
                            //Vec 1 is positive and Vec2 is a straight vertical line
                            else {
                                VecLeftM = Vec2M;
                                VecRightM = Vec1M;
                            }
                        }

                        else if (Vec1M < 0) {
                            //Vec1 is negative and Vec2 is positive
                            if (Vec2M > 0){
                                VecLeftM = Vec1M;
                                VecRightM = Vec2M;
                            }
                            //both are negative slopes
                            else if (Vec2M < 0) {
                                //Vec1 is steeper
                                if (Vec1M < Vec2M) {
                                    VecLeftM = Vec2M;
                                    VecRightM = Vec1M;
                                }
                                //Vec2 is steeper
                                else if (Vec1M > Vec2M) {
                                    VecLeftM = Vec1M;
                                    VecRightM = Vec2M;
                                }
                            }
                            //Vec1 is negative and Vec2 is a straight horizontal line
                            else if (Vec2M == 0) {
                                VecLeftM = Vec2M;
                                VecRightM = Vec1M;
                            }
                            //Vec1 is negative and Vec2 is a straight vertical line
                            else {
                                VecLeftM = Vec1M;
                                VecRightM = Vec2M;
                            }
                        }

                        else if (Vec1M == 0) {
                            //Vec1 is a straight horizontal line and Vec2 is positive
                            if (Vec2M > 0) {
                                VecLeftM = Vec2M;
                                VecRightM = Vec1M;
                            }
                            //Vec1 is a straight horizontal line and Vec2 is negative
                            else if (Vec2M < 0) {
                                VecLeftM = Vec1M;
                                VecRightM = Vec2M;
                            }
                        }

                        else {
                            //Vec1 is a straight vertical line and Vec2 is positive
                            if (Vec2M > 0) {
                                VecLeftM = Vec1M;
                                VecRightM = Vec2M;
                            }
                            //Vec1 is a straight vertical line and Vec2 is negative
                            else if (Vec2M < 0) {
                                VecLeftM = Vec2M;
                                VecRightM = Vec1M;
                            }
                        }

                    /*
                    double seansAngle1 = Math.acos((Vec1X0 - Vec1X1)/Math.sqrt(((Vec1X0-Vec1X1)^2)+((Vec1Y0-Vec1Y1)^2));
                    double seansAngle2 = Math.acos((Vec2X0 - Vec2X1)/Math.sqrt(((Vec2X0-Vec2X1)^2)+((Vec2Y0-Vec2Y1)^2));

                    if (seansAngle1 < seansAngle2){
                        VecLeftM = Vec2M;
                        VecRightM = Vec1M;
                    }
                    else if(seansAngle1 > seansAngle2){
                        VecLeftM = Vec1M;
                        VecRightM = Vec2M;
                    }
                    */

                    System.out.println("Intersect coordinates are (" + intersectX + "," + intersectY + ")");

                    }
                }
            }
            System.out.println();
        }
        catch (java.lang.NullPointerException e) {
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