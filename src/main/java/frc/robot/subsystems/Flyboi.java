package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The shooter, composed of a single flywheel.
 */
public class Flyboi extends Subsystem {

    private CANSparkMax leftFlywheel;
    private CANSparkMax rightFlywheel;
    public CANEncoder leftEncoder;
    public CANEncoder rightEncoder;
    
    private SimpleMotorFeedforward feedforward;

    public Flyboi() {
        super("Flyboi");
        this.leftFlywheel = new CANSparkMax(RobotMap.leftFlywheel, MotorType.kBrushless);
        this.rightFlywheel = new CANSparkMax(RobotMap.rightFlywheel, MotorType.kBrushless);
        this.leftEncoder = leftFlywheel.getEncoder();
        this.rightEncoder = rightFlywheel.getEncoder();

        this.feedforward = new SimpleMotorFeedforward(1.0, 1.0);
    }

    public double getPower() {
        return (leftEncoder.getVelocity()/5600);
    }

    public void setShootWithPID(double leftshoot, double rightshoot) {
        leftFlywheel.setVoltage(feedforward.calculate(leftshoot*10.9));
        rightFlywheel.setVoltage(feedforward.calculate(rightshoot*10.9));
    }

    public void initDefaultCommand() {}

    /**
     * Spins wheel motors.
     * @param speed The speed to set the flywheel to (-1 to 1).
     */
    public void spinWheel(double speed) {
        leftFlywheel.set(speed);
        rightFlywheel.set(-speed);
    }

    /**
     * Stops wheel motors.
     */
    public void stopWheel() {
        leftFlywheel.set(0);
        rightFlywheel.set(0);
    }
}