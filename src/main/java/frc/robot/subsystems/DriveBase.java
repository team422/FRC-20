package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TankDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;

/**
 * The drive base of the robot. Includes all drive train motor controllers as well as sensors such as gyros and encoders.
 */
public class DriveBase extends Subsystem {

    public WPI_TalonSRX leftMiddleMaster;
    public WPI_TalonSRX rightMiddleMaster; 

    // Toaster/comp bot
    // public WPI_VictorSPX leftFrontFollower;
    // public WPI_VictorSPX leftRearFollower;
    // public WPI_VictorSPX rightFrontFollower;
    // public WPI_VictorSPX rightRearFollower;

    //Practice bot
    public WPI_TalonSRX leftFrontFollower;
    public WPI_TalonSRX leftRearFollower;
    public WPI_TalonSRX rightFrontFollower;
    public WPI_TalonSRX rightRearFollower;

    public ADXRS450_Gyro gyro;
    private SpeedControllerGroup leftSide;
    private SpeedControllerGroup rightSide;
    public DifferentialDrive cheesyDrive; 
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;

    public int leftMotorTicks = 0;
    public int rightMotorTicks = 0;

    public DriveBase() {
        super("DriveBase");
        this.leftMiddleMaster = new WPI_TalonSRX(RobotMap.leftMiddleMaster); 
        this.rightMiddleMaster = new WPI_TalonSRX(RobotMap.rightMiddleMaster);

        //Toaster/comp bot
        // this.leftFrontFollower = new WPI_VictorSPX(RobotMap.leftFrontFollower);
        // this.leftRearFollower = new WPI_VictorSPX(RobotMap.leftRearFollower);
        // this.rightFrontFollower = new WPI_VictorSPX(RobotMap.rightFrontFollower);
        // this.rightRearFollower = new WPI_VictorSPX(RobotMap.rightRearFollower);

        //Practice bot
        this.leftFrontFollower = new WPI_TalonSRX(RobotMap.leftFrontFollower);
        this.leftRearFollower = new WPI_TalonSRX(RobotMap.leftRearFollower);
        this.rightFrontFollower = new WPI_TalonSRX(RobotMap.rightFrontFollower);
        this.rightRearFollower = new WPI_TalonSRX(RobotMap.rightRearFollower);

        leftMiddleMaster.setInverted(true);
        leftFrontFollower.setInverted(true);
        leftRearFollower.setInverted(true);

        this.gyro = new ADXRS450_Gyro(kGyroPort);
        this.leftSide = new SpeedControllerGroup(leftMiddleMaster, leftFrontFollower, leftRearFollower);
        this.rightSide = new SpeedControllerGroup(rightMiddleMaster, rightFrontFollower, rightRearFollower);        
        this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);

        leftMotorTicks = leftMiddleMaster.getSelectedSensorPosition(0);
        rightMotorTicks = rightMiddleMaster.getSelectedSensorPosition(0);
    }

    public void initDefaultCommand() {
        this.setDefaultCommand(new TankDrive());
    }

    /**
     * Sets drive train motors.
     * @param left Left side motors' velocity (-1 to 1)
     * @param right Right side motors' velocity (-1 to 1)
     */
    public void setMotors(double left, double right) {
        leftSide.set(left);
        rightSide.set(right);
    }
    
    /**
     * Sets drive train motors to zero, effectively stopping the bot.
     */
    public void stopMotors() {
        leftSide.set(0);
        rightSide.set(0);
    }

    /**
     * @return Left side position in ticks.
     */
    public int getLeftPosition() {
        return leftMiddleMaster.getSelectedSensorPosition(0) - leftMotorTicks;
    }

    /**
     * @return Right side position in ticks.
     */
    public int getRightPosition() {
        return rightMiddleMaster.getSelectedSensorPosition(0) - rightMotorTicks;
    }

    /**
     * @return Angle at which the robot is positioned in degrees
     */
    public double getGyroAngle() {
        return gyro.getAngle();       
    }

    /**
     * <p>Sends a message to the encoders for them to zero.</p>
     * This does not currently take place immediately; if you call this method and use the encoder values right afterwards,
     * you will likely be using old values.
     */
    public void zeroEncoderPosition() {
        leftMotorTicks = leftMiddleMaster.getSelectedSensorPosition(0);
        rightMotorTicks = rightMiddleMaster.getSelectedSensorPosition(0);
    }

    /**
     * Sets the gyro angle to zero.
     */
    public void zeroGyroAngle() {
        gyro.reset();
    }
}