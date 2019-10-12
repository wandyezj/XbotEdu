package xbot.edubot.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.edubot.subsystems.drive.DriveSubsystem;
import xbot.edubot.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends DriveToOrientationCommand{
    
    PoseSubsystem pose;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose,  CommonLibFactory clf, PIDFactory pf) {
        super(driveSubsystem, pose, clf, pf);
        this.pose = pose;
    }
    

    @Override
    public void initialize() {
        super.initialize();
        // should be current heading + 90
        double goal = pose.getCurrentHeading().getValue() + 90;
        super.setTargetHeading(goal);
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isFinished() {
        // Run until the the robot has completed the turn
        return super.isFinished();
    }

}
