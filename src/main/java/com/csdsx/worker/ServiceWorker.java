package com.csdsx.worker;

import com.csdsx.Msg.MsgHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Administrator on 2016-03-12.
 */
public class ServiceWorker implements Runnable{
    private static Logger log = LogManager.getLogger(ServiceWorker.class);
    private String name;
    private MsgHandler req;

    public MsgHandler getReq() {
        return req;
    }

    public void setReq(MsgHandler req) {
        this.req = req;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceWorker(MsgHandler req) {
        this.req = req;
    }

    @Override
    public void run() {
        try {
            //req进行解密
            req.decrypt();
            log.info(Arrays.toString(req.getData()));
            req.commit();
            //根据req的type 选择不同的函数处理
//            CallFunc.invoke(req.getType(), req);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
