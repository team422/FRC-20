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

    // Comp bot/Practice bot
    
        
    public WPI_VictorSPX leftFrontFollowerVictor;
    public WPI_VictorSPX leftRearFollowerVictor;
    public WPI_VictorSPX rightFrontFollowerVictor;
    public WPI_VictorSPX rightRearFollowerVictor;

    //Toaster
    public WPI_TalonSRX leftFrontFollowerTalon;
    public WPI_TalonSRX leftRearFollowerTalon;
    public WPI_TalonSRX rightFrontFollowerTalon;
    public WPI_TalonSRX rightRearFollowerTalon;

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

        leftMiddleMaster.setInverted(true);

        if (RobotMap.botName == RobotMap.BotNames.COMPETITION || RobotMap.botName == RobotMap.BotNames.PRACTICE){
            //Practice/comp bot
            this.leftFrontFollowerVictor = new WPI_VictorSPX(RobotMap.leftFrontFollower);
            this.leftRearFollowerVictor = new WPI_VictorSPX(RobotMap.leftRearFollower);
            this.rightFrontFollowerVictor = new WPI_VictorSPX(RobotMap.rightFrontFollower);
            this.rightRearFollowerVictor = new WPI_VictorSPX(RobotMap.rightRearFollower);
            
            leftFrontFollowerVictor.setInverted(true);
            leftRearFollowerVictor.setInverted(true);
    
            this.leftSide = new SpeedControllerGroup(leftMiddleMaster, leftFrontFollowerVictor, leftRearFollowerVictor);
            this.rightSide = new SpeedControllerGroup(rightMiddleMaster, rightFrontFollowerVictor, rightRearFollowerVictor);
            this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);
       
        } else if (RobotMap.botName == RobotMap.BotNames.TOASTER) {
            //Toaster
            this.leftFrontFollowerTalon = new WPI_TalonSRX(RobotMap.leftFrontFollower);
            this.leftRearFollowerTalon = new WPI_TalonSRX(RobotMap.leftRearFollower);
            this.rightFrontFollowerTalon = new WPI_TalonSRX(RobotMap.rightFrontFollower);
            this.rightRearFollowerTalon = new WPI_TalonSRX(RobotMap.rightRearFollower);

            leftFrontFollowerTalon.setInverted(true);
            leftRearFollowerTalon.setInverted(true);
    
            this.leftSide = new SpeedControllerGroup(leftMiddleMaster, leftFrontFollowerTalon, leftRearFollowerTalon);
            this.rightSide = new SpeedControllerGroup(rightMiddleMaster, rightFrontFollowerTalon, rightRearFollowerTalon);
            this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);
        }
        

        this.gyro = new ADXRS450_Gyro(kGyroPort);
        

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