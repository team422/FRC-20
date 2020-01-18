package frc.robot;

import edu.wpi.first.wpilibj.I2C;

public class Pixy {
    
    public static I2C ballPixyI2C; //I2C bus for Pixy to the RIO
    public int blockX; //x value for the yellow powercell/ball 
    
    public Pixy() {
        ballPixyI2C = new I2C(I2C.Port.kOnboard, 0x1); //the port the roboRIO that the Pixy is using
    }

    public void readPixy(){
        
        byte[] pixyValues = new byte[64]; //declares byte array

        while (true) { //keeps reading the ball position (to check for change)

            ballPixyI2C.readOnly(pixyValues, 64); //reads only from I2C port

            for (int i=0; i<20; i++) { //loops for the first 20 bytes of data for all of he data use 64
                
                System.out.print(pixyValues[i]);
            
            }
        }
    }
}