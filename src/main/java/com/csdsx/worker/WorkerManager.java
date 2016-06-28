package com.csdsx.worker;

import com.csdsx.Msg.MsgHandler;
import com.csdsx.queue.MsgQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2016-03-12.
 */
public class WorkerManager {
    public static void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Worker work = new Worker();
        executorService.execute(work);
        MyMonitorThread monitor = new MyMonitorThread(work.getExecutor(), 3);
        executorService.execute(monitor);
//        executorService.submit(new Worker());
    }

}
class Worker implements Runnable {
    private static Logger log = LogManager.getLogger(Worker.class);
    private volatile boolean isRunning = true;
    private ThreadPoolExecutor executor;

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public Worker() {
        executor = new ThreadPoolExecutor(1, 3,
                60*10L, TimeUnit.SECONDS,
//            new SynchronousQueue<Runnable>());
//                      new ArrayBlockingQueue<Runnable>(5));
        new LinkedBlockingQueue<Runnable>());
    }


    @Override
    public void run() {
        while (isRunning) {
            Queue queue = MsgQueue.getInstance().getMsgQueue();
            MsgHandler req = null;
            synchronized (queue) {
                if(!queue.isEmpty()) {
                    req = (MsgHandler) queue.poll();
                }
            }
            if(req!= null) {
                //TODO 在这里加上过滤器 比如session过滤器 session超时 就重新分配token或者私钥
                executor.execute(new ServiceWorker(req));
            }
            //try {
            //    Thread.sleep(5);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }
}
