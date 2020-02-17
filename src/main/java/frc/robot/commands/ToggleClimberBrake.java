package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class ToggleClimberBrake extends Command {

    public ToggleClimberBrake() {
        super("ToggleClimberBrake");
        requires(Subsystems.climber);
    }

    @Override
    public void initialize() {
      if (RobotMap.isClimberBrakeToggled) {
        Subsystems.climber.brakeDown();
        RobotMap.isClimberBrakeToggled = false;
      } else {
        Subsystems.climber.brakeRelease();
        RobotMap.isClimberBrakeToggled = true;
      }
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {}

    @Override
    public void end() {}
}