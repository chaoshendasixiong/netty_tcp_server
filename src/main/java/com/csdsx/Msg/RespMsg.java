package com.csdsx.Msg;

/**
 * Created by Administrator on 2016-03-12.
 */
public class RespMsg {
    private short type;
    public String result;
    public int status;
    public String msg;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public byte[] toByteArray() {
        return result.getBytes();
    }
}
