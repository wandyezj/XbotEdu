package xbot.edubot.linear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

import xbot.edubot.BaseDriveTest;
import xbot.edubot.subsystems.drive.commands.DriveToPositionCommand;

public class DriveToPositionCommandTest extends BaseDriveTest {

    double target_distance = 5;
    protected Timer asyncTimer;
    protected AsyncLinearIntervalJob asyncJob;
    protected double periodMultiplier = 1;
    LinearEngine engine;
    DriveToPositionCommand command;
    private int loops;
    
    public static interface AsyncLinearIntervalJob {
        void onNewStep(LinearEnvironmentState envState);
    }
    
    public void setAsAsync(AsyncLinearIntervalJob asyncIntervalJob) {
        this.asyncJob = asyncIntervalJob;
    }
    
    public static class LinearEnvironmentState {
        public double velocity;
        public double distance;
        public boolean isFinished;
        public int loops;
        
        public LinearEnvironmentState(double velocity, double distance, boolean isDone, int loops) {
            this.velocity = velocity;
            this.distance = distance;
            this.isFinished = isDone;
            this.loops = loops;
        }
    }
    
    public void setAsyncPeriodMultiplier(double newMultiplier) {
        if(Math.abs(this.periodMultiplier - newMultiplier) > 0.01) {
            this.periodMultiplier = newMultiplier;
            
            asyncTimer.cancel();
            startAsyncTimer();
        }
    }
    
    public void vizRun() {
        command = 
                injector.getInstance(DriveToPositionCommand.class);
        command.setTargetPosition(target_distance);
                
        command.initialize();
        engine = new LinearEngine();
        
        startAsyncTimer();    
    }
    
    public void startAsyncTimer() {
        asyncTimer = new Timer();
        asyncTimer.schedule(
            new TimerTask() {
                @Override
                public void run() {
                    command.execute();
                    engine.step(getForwardPower());
                    loops++;
                    pose.odometer.setDistance(engine.getDistance());
                    
                    asyncJob.onNewStep(
                        new LinearEnvironmentState(
                                engine.getVelocity(), 
                                engine.getDistance(), 
                                command.isFinished(),
                                loops)
                    );
                }
            }, 0, (int)(1000*this.periodMultiplier));
    }
    
    public void stopTestEnv() {
        asyncTimer.cancel();
    }
    
    @Test
    public void test() {
        
        LinearEngine engine = new LinearEngine();
        
        pose.odometer.setDistance(0);
        
        DriveToPositionCommand command = 
                injector.getInstance(DriveToPositionCommand.class);
        command.setTargetPosition(target_distance);
        
        command.initialize();
                
        double error_threshold = 0.2;
        boolean isFinished = false;
        double counter = 0;
        
        for(int i = 0; i < 300; i++) {
            
            engine.step(getForwardPower());
            counter++;
            
            // model change in position based on motor power
            pose.odometer.setDistance(engine.getDistance());
            
            System.out.println("Loop: " 
                + i 
                + "  Velocity: " 
                + engine.getVelocity() 
                + "  Distance: " 
                + engine.getDistance() 
                + "  Power: " 
                + getForwardPower()
            );
            
            if(command.isFinished()) {
                isFinished = true;
                break;
            }
            
            command.execute();
            
        }
        
        System.out.println("=============");
        System.out.println("To pass, the robot must be near the goal, ");
        System.out.println("  with almost 0 velocity, and report that it is finished.");
        System.out.println("  It also must finish within 300 loops.");
        
        double distance = pose.odometer.getDistance();
        boolean isNearGoal = (distance > target_distance - error_threshold) && (distance < target_distance + error_threshold); 
        boolean isLowLoopCount = counter < 300;
        boolean isSlow = (engine.getVelocity() > -0.1) && (engine.getVelocity() < 0.1);
        
        System.out.println("Your stats:");
        System.out.println("Loop count: " + (int)counter + ", Pass=" + isLowLoopCount);
        System.out.println("Your command's final distance: " + pose.odometer.getDistance() + ", Pass=" + isNearGoal);
        System.out.println("Your command's final velocity: " + engine.getVelocity() + ", Pass=" + isSlow);
        System.out.println("Is your command finished: " + isFinished + ", Pass=" + isFinished);
        
        assertTrue("Verify command reports successfully finished", isFinished);
        command.end();
        assertEquals("Make sure robot is close to target position within " + error_threshold, 
                target_distance, pose.odometer.getDistance(), error_threshold);
        assertEquals("Make sure robot has come to a stop, not just flying past the target position.", 
                0.0, engine.getVelocity(), 0.1);
        
        
    }
    
    double getForwardPower() {
        return drive.frontLeft.getMotorOutputPercent();
    }

}
