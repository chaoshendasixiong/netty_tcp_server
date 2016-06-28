package SocketTest;

import com.csdsx.Msg.MsgHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2016-03-13.
 */
public class ClientHandler extends SimpleChannelInboundHandler<MsgHandler> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgHandler o) throws Exception {
        //ByteBuf buf = (ByteBuf)o;
        //byte[] req = new byte[buf.readableBytes()];
        //buf.readBytes(req);
        //String body = new String(req, "UTF-8");
        //System.out.println(body);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel--active==============");
        MsgHandler resp = new MsgHandler();
        short type = 99;
        resp.setType(type);
        String result = "ddd";
        resp.setResult(result);
        ctx.writeAndFlush(resp);
        ctx.close();
    }

}
