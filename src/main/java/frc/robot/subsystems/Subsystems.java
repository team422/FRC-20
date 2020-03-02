package frc.robot.subsystems;

import frc.robot.subsystems.rplidar.RpLidarDeviceInfo;
import frc.robot.subsystems.rplidar.RpLidarHeath;
import frc.robot.subsystems.rplidar.RpLidarListener;
import frc.robot.subsystems.rplidar.RpLidarLowLevelDriver;
import frc.robot.subsystems.rplidar.RpLidarMeasurement;
import frc.robot.subsystems.rplidar.TestListener;

/**
 * Contains instances of all subsystems on the robot.
 */
public class Subsystems {

    // public static final Climber climber = new Climber();
    public static final DriveBase driveBase = new DriveBase();
    public static final Flyboi flyboi = new Flyboi();
    public static final Helix helix = new Helix();
    public static final Intake intake = new Intake();
    public static final Pixy pixy = new Pixy();
    public static final RpLidarListener listener = new TestListener();
    public static final RpLidarLowLevelDriver lidar = new RpLidarLowLevelDriver("USB", listener);

}