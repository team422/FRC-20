package frc.robot.userinterface;
 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
 
public class Joystix {
 
    private Joystick joystick;
    public JoystickButton bumper;
 
    public Joystix(int port) {
        this.joystick = new Joystick(port);
        this.bumper = new JoystickButton(joystick, 1);
    }
 
    public double getJoystickY() { return joystick.getRawAxis(1); }
 
}
