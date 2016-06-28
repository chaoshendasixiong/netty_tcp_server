package com.csdsx.Msg;

import io.netty.channel.Channel;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016-03-12.
 */
public class MsgHandler {
    private Channel ch;
    private short type;
    private String param;
    public String result;
    private byte[] data;

    public void decrypt() throws UnsupportedEncodingException{
        String param = new String(data, "UTF-8");
        this.setParam(param);
    }
    public void encrypt() {
        this.setData(this.result.getBytes());
    }
    public void commit() {
//        this.encrypt();
        this.ch.writeAndFlush(this.param);
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
