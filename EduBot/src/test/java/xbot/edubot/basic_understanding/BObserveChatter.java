package xbot.edubot.basic_understanding;

import org.apache.log4j.Logger;
import org.junit.Test;

import xbot.common.command.XScheduler;
import xbot.common.injection.BaseWPITest;

public class BObserveChatter extends BaseWPITest{
    
    protected Logger log;
    
    @Test
    public void watchChatter() {
        log = Logger.getLogger(BObserveChatter.class);
        
        ChatCommandThatEnds cmd = injector.getInstance(ChatCommandThatEnds.class);
        cmd.setRunWhenDisabled(true);
        cmd.start();
        XScheduler scheduler = injector.getInstance(XScheduler.class);
        
        for (int i = 0; i < 100; i++) {
            
            if (i % 10 == 0) 
            {
                log.info("SCHEDULER ON STEP " + i);
            }
            
            scheduler.run();
        }
    }
}
