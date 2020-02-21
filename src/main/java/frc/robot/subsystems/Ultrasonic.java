package frc.robot.subsystems;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {

    private AnalogInput ultrasonic;
    private double previousUltrasonicVoltage = 10000;
    private double ultrasonicVoltage = 0;
    private double ultrasonicInches = 0;

    public Ultrasonic() {
        ultrasonic = new AnalogInput(RobotMap.ultrasonicAnalogPort);
    }

    public void updateVoltage() {
        ultrasonicVoltage = previousUltrasonicVoltage*.3 + ultrasonic.getValue()*.7;
        ultrasonicInches = (Math.floor(10*(0.052323*ultrasonicVoltage-0.7635)))/10;
        previousUltrasonicVoltage = ultrasonicVoltage;
    }

    public double getInchesAway() {
        return ultrasonicInches;
    }

}