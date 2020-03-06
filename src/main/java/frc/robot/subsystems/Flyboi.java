package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;

/**
 * The shooter, composed of a single flywheel.
 */
public class Flyboi extends Subsystem {

    private CANSparkMax leftFlywheel;
    private CANSparkMax rightFlywheel;
    public CANEncoder leftEncoder;
    public CANEncoder rightEncoder;
    private SimpleMotorFeedforward feedforward;

    public double wheelSpeed = 0.810;

    public Flyboi() {
        super("Flyboi");
        this.leftFlywheel = new CANSparkMax(RobotMap.leftFlywheel, MotorType.kBrushless);
        this.rightFlywheel = new CANSparkMax(RobotMap.rightFlywheel, MotorType.kBrushless);
        this.leftEncoder = leftFlywheel.getEncoder();
        this.rightEncoder = rightFlywheel.getEncoder();

        this.feedforward = new SimpleMotorFeedforward(1.05, 1.0);
    }

    public void initDefaultCommand() {}

    /**
     * Spins wheel motors. Recommended to use <code>setShootVoltage</code> instead.
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

    /**
     * @return The velocity of the flywheel as found by the left flywheel encoder (around -1 to 1).
     */
    public double getPower() {
        return (leftEncoder.getVelocity()/5600);
    }

    /**
     * Spins wheel motors at a certain speed by setting its desired voltage.
     * This will attempt to keep the flywheel velocity at a little above the velocity input, according to <code>getPower</code>.
     * The actual velocity will fluctuate a little, but will attempt to stay at this voltage no matter the battery level.
     * @param speed The speed to set the flywheel to (around -1 to 1).
     */
    public void setShootVoltage(double speed) {
        leftFlywheel.setVoltage(feedforward.calculate(speed*10.9));
        rightFlywheel.setVoltage(feedforward.calculate(-speed*10.9));
    }
}