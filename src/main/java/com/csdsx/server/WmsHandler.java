package com.csdsx.server;

import com.csdsx.Msg.MsgHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by xxsy on 2016/3/11.
 */
public class WmsHandler extends SimpleChannelInboundHandler<MsgHandler>{
    int count = 0;
    private static Logger log = LogManager.getLogger(WmsHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgHandler req) throws Exception {
        req.setCh(channelHandlerContext.channel());
//        MsgQueue.pushQueue(req);
//        String param = new String(req.getData()).substring(4);
//        log.info(param);
//        channelHandlerContext.writeAndFlush(param);
        //解析打印
        /*
        byte[] data = req.getData();
        AddressBookProtos.Person person_ = AddressBookProtos.Person.parseFrom(data);
        log.info(person_.getName());
        log.info(person_.getId());
        log.info(person_.getEmail());


        AddressBookProtos.Person.Builder builder = AddressBookProtos.Person.newBuilder();
        builder.setName("client");
        builder.setId((int)System.currentTimeMillis()/1000);
        builder.setEmail("client.eamil");
        AddressBookProtos.Person person = builder.build();
        byte[] buf = person.toByteArray();
        req.setData(buf);
        */
        count++;
        byte[] data = req.getData();
        log.info("第"+count+"次请求:"+new String(data));
        channelHandlerContext.writeAndFlush(req);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        ChannelUtil.group.remove(ctx.channel());
        log.info(cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.group.add(ctx.channel());
        super.channelActive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
                .remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        log.info("new connection: "+clientIP+":"+port);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.group.remove(ctx.channel());
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
                .remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        log.info("close connection: "+clientIP);
    }
}
