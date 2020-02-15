package frc.robot.commands;

import frc.robot.subsystems.Subsystems;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class TogglePistonPin extends Command{

  public TogglePistonPin(){
    super("Toggle Piston Pin");
    requires(Subsystems.helix);
  }

  @Override
    public void initialize() {
      if (RobotMap.isPistonToggled) {
        Subsystems.helix.cellStopIn();
        RobotMap.isPistonToggled = false;
    } else {
        Subsystems.helix.cellStopOut();
        RobotMap.isPistonToggled = true;
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
