package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class ToggleIntake extends Command{

  public ToggleIntake(){
    super("Toggle Intake");
    requires(Subsystems.intake);
  }

  @Override
    public void initialize() {
      if (RobotMap.isIntakeToggled) {
        Subsystems.intake.intakeExtend();
        RobotMap.isIntakeToggled = false;
    } else {
        Subsystems.intake.intakeRetract();
        RobotMap.isIntakeToggled = true;
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
