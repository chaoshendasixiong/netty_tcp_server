package com.csdsx.redis;

import redis.clients.jedis.*;

import java.util.List;

/**
 * Created by xxsy on 2016/3/15.
 */
public class RedisUtil {
    public static JedisPool pool;
    public static String host = "192.168.171.128";
    public static int port = 6379;
    public static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        pool = new JedisPool(config, host, port);
    }

    public synchronized static Jedis getJedis() {
        try {
            if (pool != null) {
                Jedis resource = pool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void set(String key, String value) {
        Jedis jedis =null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            //logger.warn("del", key, e);
        } finally {
            jedis.close();
        }
    }

    public static String get(String key) {
        Jedis jedis =null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            //logger.warn("del", key, e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public static void dosth() {
        Jedis jedis =null;
        try {
            jedis = RedisUtil.getJedis();
            //jedis.del(key.toString());
        } catch (Exception e) {
            //logger.warn("del", key, e);
        } finally {
            jedis.close();
        }
    }

    public static void pipeline() {
        Jedis jedis =null;
        try {
            jedis = RedisUtil.getJedis();
            Pipeline pipe = jedis.pipelined();
            List<Object> results = pipe.syncAndReturnAll();
        } catch (Exception e) {
            //logger.warn("del", key, e);
        } finally {
            jedis.close();
        }
    }

    public static void doTx() {
        Jedis jedis =null;
        try {
            jedis = RedisUtil.getJedis();
            Transaction tx = jedis.multi();
            List<Object> results = tx.exec();
            // do you business
            tx.discard();//cancel the tx if no all arive
            //jedis.del(key.toString());
        } catch (Exception e) {
            //logger.warn("del", key, e);
        } finally {
            jedis.close();
        }
    }
}
