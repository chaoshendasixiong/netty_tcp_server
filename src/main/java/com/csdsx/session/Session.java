package com.csdsx.session;

import com.csdsx.bean.User;

/**
 * Created by xxsy on 2016/4/7.
 */
public class Session {
    //令牌 32位字符串
    private String token;
    //加密私钥
    private String private_key;
    //加密公钥
    private String public_key;
    //session创建时间
    private int createtime;
    //用户对象
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
