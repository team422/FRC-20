package frc.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.RobotMap;
import frc.robot.Direction;

public class Intake extends Subsystem {   

    private WPI_TalonSRX intake;
    
    public Intake(){
        super("Intake");
        this.intake = new WPI_TalonSRX(RobotMap.intake);
    }

    protected void initDefaultCommand() {} 
    
    public void setIntakeMotors(double power) {
        intake.set(ControlMode.PercentOutput, -power);
    }    public void stopIntakeMotors() {
        intake.set(ControlMode.PercentOutput, 0.0);
    }



    public void intake(double power, Direction direction) {
        if (direction == Direction.Up) {
            intake.set(ControlMode.PercentOutput, -power);
        } else if (direction == Direction.Down) {
            intake.set(ControlMode.PercentOutput, power);
        } else {
            intake.set(ControlMode.PercentOutput, 0.0);
        }
    }
}