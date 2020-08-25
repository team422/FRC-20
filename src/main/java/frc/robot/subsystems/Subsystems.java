package frc.robot.subsystems;

import frc.robot.subsystems.rplidar.*;

/**
 * Contains instances of all subsystems on the robot.
 */
public class Subsystems {

    public static final DriveBase driveBase = new DriveBase();
    public static final Flyboi flyboi = new Flyboi();
    public static final Helix helix = new Helix();
    public static final Intake intake = new Intake();
    public static final RpLidarListener listener = new TestListener();
    public static final RpLidarLowLevelDriver lidar = new RpLidarLowLevelDriver("USB", listener);

}