package xbot.edubot.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.math.XYPair;
import xbot.edubot.operator_interface.OperatorInterface;
import xbot.edubot.subsystems.drive.DriveSubsystem;

public class ArcadeDriveWithJoysticksCommand extends BaseCommand {

    DriveSubsystem drive;
    OperatorInterface operate;
    
    @Inject
    public ArcadeDriveWithJoysticksCommand(DriveSubsystem driveSubsystem, OperatorInterface oi) {
        drive = driveSubsystem;
        operate = oi;
        this.requires(drive);
    }
    
    @Override
    public void initialize() {
        
    }

    public double round(double r) {
        if (r < -1) {
            return -1;
        }

        if (r > 1) {
            return 1;
        }

        return r;
    }

    @Override
    public void execute() {
        XYPair joystick = operate.gamepad.getLeftVector();

        double power = joystick.y;
        double rotation = joystick.x;

        double left = round(power + rotation);
        double right = round(power - rotation);

        drive.tankDrive(left, right);

    }

}
