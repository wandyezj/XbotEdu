package xbot.edubot.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.edubot.subsystems.drive.DriveSubsystem;

public class TogglePrecisionDriveCommand extends BaseCommand {

    DriveSubsystem drive;

    @Inject
    public TogglePrecisionDriveCommand(DriveSubsystem driveSubsystem) {
        drive = driveSubsystem;
    }
    
    @Override
    public void initialize() {
        // Here, you want to call the DriveSubsystem and tell it to change its precision mode.
        // This means you'll need to add a new method into DriveSubsystem, and there are two
        // major ways to do this:
        // 1) Create something like GetPrecisionMode() and SetPrecisionMode() on the DriveSubsystem. Your
        //    command can read the current value, change it, and set it back into the subsystem.
        // 2) Create a TogglePrecisionMode() on the DriveSubsystem. The system will internally change
        //    the current mode.
        // In all of these cases you'll need to have the mode somehow affect the TankDrive method.

        drive.togglePrecisionDrive();
    }

    @Override
    public void execute() {
        // No need to put any code here - since we want the toggle to run once, initialize() is the
        // best place to put the code.
    }
    
    @Override
    public boolean isFinished() {
        // Commands keep running until they are finished.
        // Since we want this command to just run once (toggling precision mode), we 
        // say that the command is finished right away.
        return true;
    }

}
