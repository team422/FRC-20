package frc.robot.userinterface;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController {

    private Joystick joystick;
    public final JoystickButton A, B, X, Y, LB, RB, BACK, START;

    public XboxController(int port) {
        this.joystick = new Joystick(port);
        this.A = new JoystickButton(joystick, 1);
        this.B = new JoystickButton(joystick, 2);
        this.X = new JoystickButton(joystick, 3);
        this.Y = new JoystickButton(joystick, 4);
        this.LB = new JoystickButton(joystick, 5);
        this.RB = new JoystickButton(joystick, 6);
        this.BACK = new JoystickButton(joystick, 7);
        this.START = new JoystickButton(joystick, 8);
    }

    public double getLeftJoystickX() { return joystick.getRawAxis(0); }

    public double getLeftJoystickY() { return joystick.getRawAxis(1); }

    public double getRightJoystickX() { return joystick.getRawAxis(4); }

    public double getRightJoystickY() { return joystick.getRawAxis(5); }

    public double getLeftTrigger() { return joystick.getRawAxis(2); }

    public double getRightTrigger() { return joystick.getRawAxis(3); }

    public int getPOVAngle() {
        return joystick.getPOV(0);
    }
}