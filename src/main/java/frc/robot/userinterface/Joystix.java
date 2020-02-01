package frc.robot.userinterface;
 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
 
public class Joystix {
 
    public Joystick joystick1;
    public JoystickButton TRIGGERED;
 
    public Joystix(int port) {
        this.joystick1 = new Joystick(port);
        this.TRIGGERED = new JoystickButton(joystick1, 1);
    }
 
    public double getJoystickY() { return joystick1.getRawAxis(1); }
 
}
