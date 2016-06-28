package com.csdsx.Msg;

import io.netty.channel.Channel;

/**
 * Created by Administrator on 2016-03-12.
 */
public class ReqMsg {
    private Channel ch;
    private String param;
    private short type;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Channel getCh() {
        return ch;
    }

    public void setCh(Channel ch) {
        this.ch = ch;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
