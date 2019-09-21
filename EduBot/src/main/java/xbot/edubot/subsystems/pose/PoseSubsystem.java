package xbot.edubot.subsystems.pose;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.MockDistanceSensor;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    public MockDistanceSensor odometer;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propMan) {
        super(clf, propMan);
        odometer = new MockDistanceSensor();
    }

    public double getPosition() {
        return odometer.getDistance();
    }

    @Override
    protected double getLeftDriveDistance() {
        return odometer.getDistance();
    }

    @Override
    protected double getRightDriveDistance() {
        return odometer.getDistance();
    }

}