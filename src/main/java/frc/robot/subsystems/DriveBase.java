package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;

public class DriveBase extends Subsystem {

    public Talon leftMiddleMaster;
    public Talon rightMiddleMaster;

    public Victor leftFrontFollower;
    public Victor leftRearFollower;
    public Victor rightFrontFollower;
    public Victor rightRearFollower;        
    
    // public Talon leftFrontFollower;
    // public Talon leftRearFollower;
    // public Talon rightFrontFollower;
    // public Talon rightRearFollower;

    public ADXRS450_Gyro gyro;
    private SpeedControllerGroup leftSide;
    private SpeedControllerGroup rightSide;
    public DifferentialDrive cheesyDrive; 
    //public DifferentialDriveCorrection cheesyDrive; 
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;

    public DriveBase() {
        super("DriveBase");
        this.leftMiddleMaster = new Talon(RobotMap.leftMiddleMaster); 
        this.rightMiddleMaster = new Talon(RobotMap.rightMiddleMaster);
        
        this.leftFrontFollower = new Victor(RobotMap.leftFrontFollower);
        this.leftRearFollower = new Victor(RobotMap.leftRearFollower);
        this.rightFrontFollower = new Victor(RobotMap.rightFrontFollower);
        this.rightRearFollower = new Victor(RobotMap.rightRearFollower);

        //PBOT
        // this.leftFrontFollower = new WPI_TalonSRX(RobotMap.leftFrontFollower);
        // this.leftRearFollower = new WPI_TalonSRX(RobotMap.leftRearFollower);
        // this.rightFrontFollower = new WPI_TalonSRX(RobotMap.rightFrontFollower);
        // this.rightRearFollower = new WPI_TalonSRX(RobotMap.rightRearFollower);

        leftMiddleMaster.setInverted(true);
        leftFrontFollower.setInverted(true);
        leftRearFollower.setInverted(true);

        this.gyro = new ADXRS450_Gyro(kGyroPort);
        this.leftSide = new SpeedControllerGroup(leftMiddleMaster, leftFrontFollower, leftRearFollower);
        this.rightSide = new SpeedControllerGroup(rightMiddleMaster, rightFrontFollower, rightRearFollower);        
        this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);
        //this.cheesyDrive = new DifferentialDriveCorrection(leftSide, rightSide);
    }

    public void initDefaultCommand() {this.setDefaultCommand(new TankDrive());}

    public void setMotors(double left, double right) {
        leftSide.set(left);
        rightSide.set(right);
    }

    public void stopMotors() {
        leftSide.set(0);
        rightSide.set(0);
    }

    public double getLeftPosition() {
        return leftMiddleMaster.getPosition();
    }

    public double getRightPosition() {
        return rightMiddleMaster.getPosition();
    }

    public double getGyroAngle() {
        return gyro.getAngle();       
    }

    public void zeroEncoderPosition() {
        leftMiddleMaster.setPosition(0);
        rightMiddleMaster.setPosition(0);
    }

    public void zeroGyroAngle() {
        gyro.reset();
    }
}