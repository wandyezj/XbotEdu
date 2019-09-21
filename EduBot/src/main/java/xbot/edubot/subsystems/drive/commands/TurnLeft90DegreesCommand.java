package xbot.edubot.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.edubot.subsystems.drive.DriveSubsystem;
import xbot.edubot.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {
    
    DriveSubsystem drive;
    PoseSubsystem pose;
    
    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }



}
