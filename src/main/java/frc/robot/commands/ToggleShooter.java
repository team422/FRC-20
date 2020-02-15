package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class ToggleShooter extends Command{

  public ToggleShooter(){
    super("Toggle Shooter");
    requires(Subsystems.flyboi);
  }

  public double wheelSpeed;

  @Override
    public void initialize() {
      if (RobotMap.isShooterToggled) {
        Subsystems.flyboi.spinWheel(wheelSpeed);
        RobotMap.isShooterToggled = false;
    } else {
        Subsystems.flyboi.stopWheel();
        RobotMap.isShooterToggled = true;
      }
    }

  @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
    }

    @Override
    public void end() {
    }
}
