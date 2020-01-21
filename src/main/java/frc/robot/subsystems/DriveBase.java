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

public class DriveBase extends Subsystem {

    public WPI_TalonSRX leftMiddleMaster;
    public WPI_TalonSRX rightMiddleMaster;

    //Toaster/comp bot
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

    public int getLeftPosition() {
        return leftMiddleMaster.getSelectedSensorPosition(0);
    }

    public int getRightPosition() {
        return rightMiddleMaster.getSelectedSensorPosition(0);
    }

    public double getGyroAngle() {
        return gyro.getAngle();       
    }

    public void zeroEncoderPosition() {
        leftMiddleMaster.setSelectedSensorPosition(0,0,10);
        rightMiddleMaster.setSelectedSensorPosition(0,0,10);
        //The below generally doesn't work.
        // int leftPos = getLeftPosition();
        // if (leftMiddleMaster.setSelectedSensorPosition(0,0,10).value != 0) {
        //     System.out.println("ahhhhhh on left");
        // }
        // for(int i = 0; Math.abs(getLeftPosition()) > 100 && i < 1000; i++) {
        //     if (i == 999) {
        //         System.out.println("Took too long to zero left encoder (" + getLeftPosition() + ")");
        //     } else {
        //         if (leftPos != getLeftPosition()) {
        //             System.out.println("LChanged from " + leftPos + " to " + getLeftPosition() + " at " + i);
        //         }
        //     }
        // }
        // int rightPos = getRightPosition();
        // if (rightMiddleMaster.setSelectedSensorPosition(0,0,10).value != 0) {
        //     System.out.println("ahjdsgfhg on right");
        // }
        // for(int i = 0; Math.abs(getRightPosition()) > 100 && i < 1000; i++) {
        //     if (i == 999) {
        //         System.out.println("Took too long to zero right encoder (" + getRightPosition() + ")");
        //     } else {
        //         if (rightPos != getRightPosition()) {
        //             System.out.println("RChanged from " + rightPos + " to " + getRightPosition() + " at " + i);
        //         }
        //     }
        // }
    }

    public void zeroGyroAngle() {
        gyro.reset();
    }
}