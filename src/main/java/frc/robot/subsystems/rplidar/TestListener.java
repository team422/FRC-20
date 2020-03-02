package frc.robot.subsystems.rplidar;

public class TestListener implements RpLidarListener{
    @Override
        public void handleMeasurement(RpLidarMeasurement measurement) {
            System.out.println("Angle: "+measurement.getAngle()+"Distance: " +measurement.getDistance());
            
        }
    
        @Override
        public void handleDeviceInfo(RpLidarDeviceInfo info) {
            // TODO Auto-generated method stub
            
        }
    
        @Override
        public void handleDeviceHealth(RpLidarHeath health) {
            // TODO Auto-generated method stub
            
        }
}