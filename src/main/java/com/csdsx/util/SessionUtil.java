package com.csdsx.util;

import com.csdsx.bean.User;
import com.csdsx.session.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * Created by xxsy on 2016/4/7.
 * TODO 断线快速重连 session路由白名单 防止攻击监听无效消息超过一定次数断开连接 RSA交换加密私钥
 */
public class SessionUtil {
    private static final AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
    public static void initSession(Channel channel, User user) {
        //生成token 生成session对象
        String token = GlobalUtil.genToken();
        Session _session = new Session();
        _session.setCreatetime((int) System.currentTimeMillis() / 1000);
        _session.setToken(token);
        _session.setUser(user);
        channel.attr(SESSION).set(_session);
    }
    /**
     * function:
     * <br>
     * input:
     * <br>
     * output:
     */
    public static String getKey(Channel channel) {
        Session session = channel.attr(SESSION).get();
        if(session!=null) {
            return session.getPrivate_key();
        }else {
            return null;
        }
    }
    /**
     * function:获取该通道绑定的token
     * <br>
     * input:
     * <br>
     * output:
     */
    public static String getToken(Channel channel) {
        Session session = channel.attr(SESSION).get();
        if(session!=null) {
            return session.getToken();
        }else {
            return null;
        }
    }
    /**
     * function:验证令牌是否和通道绑定的token一致
     * <br>
     * input:通道 和 客户端发来的令牌
     * <br>
     * output:是否一致
     */
    public static boolean verifySession(Channel channel, String token) {
        String _token = getToken(channel);
        return token.equals(_token);
    }
}
