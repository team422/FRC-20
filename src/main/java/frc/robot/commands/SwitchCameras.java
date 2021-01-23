package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.UsbCamera;
import frc.robot.RobotMap;

/**
 * Toggles between driver cameras seen in Shuffleboard.
 */
public class SwitchCameras extends CommandBase {

    private VideoSink switchedCamera;
    private UsbCamera camera1;
    private UsbCamera camera2;

    public SwitchCameras(VideoSink SwitchedCamera, UsbCamera Camera1, UsbCamera Camera2) {
        setName("SwitchCameras");
        switchedCamera = SwitchedCamera;
		camera1 = Camera1;
		camera2 = Camera2;
    }

    public void execute() {
        if (RobotMap.isFirstCamera) {
			switchedCamera.setSource(camera2);
			RobotMap.isFirstCamera = false;
        } else {
			switchedCamera.setSource(camera1);
			RobotMap.isFirstCamera = true;
        }
    }

    public boolean isFinished() {
        return true;
    }

}