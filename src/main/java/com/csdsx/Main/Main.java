package com.csdsx.Main;

import com.csdsx.func.CallFunc;
import com.csdsx.server.WmsServer;
import com.csdsx.worker.WorkerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    static {
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "conf" + File.separator
                + "log4j2.xml");
    }
    private static Logger log = LogManager.getLogger(Main.class);
    private static int port = 0;

    public static void initEnv() {
        String javaVersion = System.getProperty("java.version");
        log.info("javaVersion=" + javaVersion);
        char ch = javaVersion.charAt(2);
        int version = ch-'0';
        if(version < 6) {
            log.info("javaVersion=" + version + "版本最低要求1.6");
            System.exit(1);
        }
//        DBUtil.init();
    }

    public static void initConf() {
        log.info("加载自定义config.properties");
        Properties prop = new Properties();
        try {
            FileInputStream in = new FileInputStream("conf/config.properties");
            prop.load(in);
            port = Integer.valueOf(prop.getProperty("port").trim());
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        } finally {
            if(port == 0) {
                log.error("config read error!");
                System.exit(1);
            }else {
                log.info("config read success!");
            }
        }
    }
    /*
    初始化工作线程
     */
    public static void initWorker() {
        WorkerManager.init();
    }
    /*
    初始化回调方法
     */
    public static void initCallFunc() {
        CallFunc.init();
    }

    public static void main(String[] args) {
        initEnv();
        initConf();
        initWorker();
        initCallFunc();
        WmsServer.start(port);
    }
}
