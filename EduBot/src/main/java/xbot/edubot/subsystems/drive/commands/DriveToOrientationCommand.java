package xbot.edubot.subsystems.drive.commands;

import java.io.Console;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingModule;
import xbot.edubot.subsystems.drive.DriveSubsystem;
import xbot.edubot.subsystems.pose.PoseSubsystem;

public class DriveToOrientationCommand extends BaseCommand{
    
    DriveSubsystem drive;
    PoseSubsystem pose;
    PIDManager pid;
    HeadingModule headingModule;

    double goal = 0;
    // double initialHeading = 0.0;

    // double desiredHeading = 0.0;
    // // current - current tick
    // // previous - previous tick

    // double distancePrevious = 0.0;
    // double distanceCurrent = 0.0;
    // double velocityPrevious = 0.0;
    // double velocityCurrent = 0.0;
    // double accelerationPrevious = 0.0;
    // double powerPrevious = 0.0;
    // double powerCurrent = 0.0;
    // double powerAccelerationRatioCurrent = 0.6; // ideally this is constant

    // double tick = 0;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, CommonLibFactory clf, PIDFactory pf) {
        this.drive = driveSubsystem;
        this.pose = pose;

        this.pid = pf.createPIDManager("Rotate");

        this.pid.setEnableErrorThreshold(true); // Turn on distance checking
        this.pid.setErrorThreshold(0.1);
        this.pid.setEnableDerivativeThreshold(true); // Turn on speed checking
        this.pid.setDerivativeThreshold(0.1);

        // have to set p and d or nothing happens
        this.pid.setP(0.1);
        this.pid.setD(1);

        this.headingModule = clf.createHeadingModule(pid);
    }
    
    public void setTargetHeading(double heading) {
        // -180 to 180
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        // convert to reasonable heading

        // this.desiredHeading = Convert360(heading);
        this.goal = heading;
    }
    
    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        // this.finished = false;
        // this.initialHeading = GetCurrentHeading360();
        // this.distancePrevious = DistanceRemaining();
        // this.distanceCurrent = DistanceRemaining(); // also needs a direction
        // this.velocityCurrent = 0.0;
        // this.velocityPrevious = 0.0;
        // this.accelerationPrevious = 0.0;
        // this.powerPrevious = 0.0;
        // this.powerCurrent = 0.0;
        // this.powerAccelerationRatioCurrent = 0.6;

        // this.tick = 0;

        // resets the pid
        this.pid.reset();
    }

    @Override
    public void execute() {
        // ContiguousHeading currentHeading = new ContiguousHeading(pose.getCurrentHeading().getValue());
        // double difference = currentHeading.difference(this.goal);

        // where the robot should be
        // automaticaly converts
        double power = headingModule.calculateHeadingPower(this.goal);
        drive.tankDrive(-power, power);

        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the target position
        
        // How you do this is up to you. If you get stuck, ask a mentor or student for some hints!
        // UpdateCalculationsStart();

        // double distanceRemainingAbsolute = Math.abs(this.distanceCurrent);

        // // distance traveled in remaining ticks
        // double stopDistance = Math.abs(this.velocityCurrent) / 3;

        // double ticksRemaining = this.distanceCurrent / this.velocityCurrent;
        // double ticksToStop = this.velocityCurrent / this.powerAccelerationRatioCurrent;



        // // power = (error: goal - current) Kpositive - (error: current - previous) Knegative

        // double power = 1;

        // if (this.tick < 2) {

        // } if (distanceRemainingAbsolute < stopDistance) {
        //     power = -1;
        // }
        // // else if (distanceRemainingAbsolute <= 2) {
        // //     power = -0.5;
        // // } 
        // else {
        //     power = 0;
        //     finished = true;
        // }

        // // round to value
  
        // // flip the power to go right
        // if (this.distanceCurrent < 0) {
        //     power = 0 - power;
        // }

        // this.powerCurrent = RoundToRange(power, -1, 1);

        // UpdateCalculationsEnd();
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal, 
        // and you're moving fairly slowly (ideally stopped)
        // Run until the the robot has completed the turn
        return this.pid.isOnTarget();
    }

    // private double RoundToRange(double n, double min, double max) {
    //     if (n > max) {
    //         return max;
    //     }

    //     if (n < min) {
    //         return min;
    //     }
    
    //     return n;
    // }

    // private double Convert360(double value) {
    //     // heading is -180 to 180
    //     // convert to 360 degree coordinates

    //     // -180 -> 180
    //     // 180 -> 180
    //     double convertedValue = value;
    //     if (convertedValue < 0) {
    //         convertedValue += 360;
    //     }

    //     return convertedValue;
    // }

    // private double GetCurrentHeading360() {
    //     double heading = pose.getCurrentHeading().getValue();
    //     double convertedHeading = Convert360(heading);

    //     return convertedHeading;
    // }

    // // how many degrees is the robot from the desire heading?
    // // needs to be able to go negative
    // public double DistanceRemaining() {
    //     double desired = this.desiredHeading;
    //     double current = GetCurrentHeading360();

    //     // current > desired
    //     double left = current + desired;

    //     if (current < desired) {
    //         left = desired - current; 
    //     }

    //     double right = 360 - left;

    //     if (left > right) {
    //         return left;
    //     }

    //     // right is considered negative
    //     return 0 - right;
    // }


    // private void UpdateCalculationsStart() {
    //     this.distanceCurrent = DistanceRemaining();
    //     this.velocityCurrent = this.distancePrevious - this.distanceCurrent;

    //     this.accelerationPrevious = this.velocityPrevious - this.velocityCurrent;

    //     // avoid divide by zero
    //     if (this.powerPrevious != 0) {
    //         this.powerAccelerationRatioCurrent = (this.accelerationPrevious / this.powerPrevious);
    //     }
    // }

    // private void UpdateCalculationsEnd() {
    //     this.tick += 1;
    //     double power = this.powerCurrent;
        
    //      // constants make tank turn left
    //     double powerLeft = -1 * power;
    //     double powerRight = 1 * power;
    //     drive.tankDrive(powerLeft, powerRight);

    //     this.distancePrevious = this.distanceCurrent;
    //     this.velocityPrevious = this.velocityCurrent;
    //     this.powerPrevious = this.powerCurrent;
    // }


}
