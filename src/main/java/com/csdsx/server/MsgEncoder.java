package com.csdsx.server;

import com.csdsx.Msg.MsgHandler;
import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by xxsy on 2016/3/11.
 */
public class MsgEncoder extends MessageToByteEncoder<MsgHandler> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MsgHandler msg, ByteBuf byteBuf) throws Exception {
        byte[] data = msg.getData();
        int len = data==null?0:data.length;
        byteBuf.writeInt(Integer.reverseBytes(len));
//        byteBuf.writeShort(msg.getType());
        if (len > 0) {
            byteBuf.writeBytes(data);
        }
    }
    /**
     * 将int转成4字节的小字节序字节数组
     */
    public static byte[] toLittleEndian(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) i;
        bytes[1] = (byte) (i >>> 8);
        bytes[2] = (byte) (i >>> 16);
        bytes[3] = (byte) (i >>> 24);
        return bytes;
    }

    /**
     * 将小字节序的4字节的字节数组转成int
     */
    public static int getLittleEndianInt(byte[] bytes) {
        int b0 = bytes[0] & 0xFF;
        int b1 = bytes[1] & 0xFF;
        int b2 = bytes[2] & 0xFF;
        int b3 = bytes[3] & 0xFF;
        return b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
    }
}
