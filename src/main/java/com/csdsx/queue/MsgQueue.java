package com.csdsx.queue;

import com.csdsx.Msg.MsgHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2016-03-12.
 */
public class MsgQueue {

    private Queue<MsgHandler> msgQueue = new ConcurrentLinkedQueue<MsgHandler>();

    public Queue<MsgHandler> getMsgQueue() {
        return msgQueue;
    }
    private MsgQueue(){}
    public static MsgQueue getInstance(){
        return MsgQueueHolder.ins;
    }
    private static class MsgQueueHolder {
        private static MsgQueue ins = new MsgQueue();
    }

    public static void pushQueue(MsgHandler req) {
        Queue queue = getInstance().getMsgQueue();
        queue.add(req);
    }
}
