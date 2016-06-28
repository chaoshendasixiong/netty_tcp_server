package WorkerTest;

import com.csdsx.Msg.MsgHandler;
import com.csdsx.worker.WorkerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by Administrator on 2016-03-12.
 */
public class testWorker {
    static {
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "conf" + File.separator
                + "log4j2.xml");
    }
    static final Logger logger = LogManager.getLogger(testWorker.class.getName());
    public static void main(String[] args) throws InterruptedException {
        WorkerManager.init();
        logger.info("Did it again!");
        for (int i = 0; i < 20; i++) {
            MsgHandler req = new MsgHandler();
            //req.setAction(String.valueOf(i)+"  ");
            //ComonFunc.pushQueue(req);
            Thread.sleep(300);
        }
        Thread.sleep(2000000);

    }
}
