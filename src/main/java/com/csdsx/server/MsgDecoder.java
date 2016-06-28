package com.csdsx.server;

import com.csdsx.Msg.MsgHandler;
import com.csdsx.util.CrptyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by xxsy on 2016/3/11.
 */
//public class MsgDecoder extends ByteToMessageDecoder {
public class MsgDecoder extends LengthFieldBasedFrameDecoder {


    public MsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public MsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public MsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public MsgDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public MsgDecoder() {
        this(ByteOrder.LITTLE_ENDIAN, 1024*512, 0, 4, 0, 4, true);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        if (buf == null) {
            return null;
        }
//        short type = buf.readShort();// 读取类型
        byte[] data = new byte[buf.readableBytes()];// 其它数据为实际数据
        buf.readBytes(data);
        CrptyUtil.decode(data);
        MsgHandler req = new MsgHandler();
        req.setData(data);
//        req.setType(type);
        return req;
    }
}
