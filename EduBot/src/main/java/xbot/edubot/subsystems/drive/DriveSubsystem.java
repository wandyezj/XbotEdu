package xbot.edubot.subsystems.drive;

import java.io.Console;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.MockDistanceSensor;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.edubot.MockHeadingSensor;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    
    public XCANTalon frontLeft;
    public XCANTalon frontRight;
    public XCANTalon rearLeft;
    public XCANTalon rearRight;
        
    public boolean precisionDriveOn = false;

    @Inject
    public DriveSubsystem(CommonLibFactory factory) {
        // instantiate speed controllers and sensors here, save them as class members        
        frontLeft = factory.createCANTalon(1);
        rearLeft = factory.createCANTalon(3);
        frontRight = factory.createCANTalon(2);
        rearRight = factory.createCANTalon(4);
    }

    public void togglePrecisionDrive() {
        System.out.print("Toggle");
        precisionDriveOn = !precisionDriveOn;
    }
    
    public void tankDrive(double leftPower, double rightPower) {
        // You'll need to take these power values and assign them to all of the motors. As
        // an example, here is some code that has the frontLeft motor to spin according to

        double powerLeft = leftPower;
        double powerRight = rightPower;

        // Cut the power in half if in precision drive mode
        if (precisionDriveOn) {
            powerLeft /= 2;
            powerRight /= 2;
        }

        // the value of leftPower:
        frontLeft.simpleSet(powerLeft);
        rearLeft.simpleSet(powerLeft);

        frontRight.simpleSet(powerRight);
        rearRight.simpleSet(powerRight);

    }
}
