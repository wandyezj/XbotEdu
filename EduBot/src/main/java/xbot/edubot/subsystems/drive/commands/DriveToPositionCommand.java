package xbot.edubot.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.edubot.subsystems.drive.DriveSubsystem;
import xbot.edubot.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    
    double desiredPosition = 0;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }
    
    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        desiredPosition = position;
    }
    
    boolean finished = true;

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        finished = false;
    }


    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position 
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the target position
        
        // How you do this is up to you. If you get stuck, ask a mentor or student for some hints!
    


        double position = pose.getPosition();

        double remaining = desiredPosition - position;
        double power = 1;

        if (remaining <=4) {
            power = -0.25;
        }


        if (remaining < 0.2) {
            power = 0;
            finished = true;
        }


        double powerLeft = power;
        double powerRight = power;

        drive.tankDrive(powerLeft, powerRight);
    }
    
    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal, 
        // and you're moving fairly slowly (ideally stopped)
        return finished;
    }

}
