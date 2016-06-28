package com.csdsx.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xxsy on 2016/3/11.
 */
public class WmsServer {
    public static void start(int port) {
        EventLoopGroup bossgroup = new NioEventLoopGroup(1);
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        //EventLoopGroup workgroup = new NioEventLoopGroup(processorsNumber*2);//实际环境的IO线程数量
        EventLoopGroup workgroup = new NioEventLoopGroup(2);

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossgroup, workgroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 1024)//设置最大连接
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //pipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024, 0, 4, 0, 4));
//                        pipeline.addLast(new CryptoDecoder());
                        //TODO IdleStateHandler
                        pipeline.addLast(new MsgDecoder());
                        pipeline.addLast(new MsgEncoder());

//                        pipeline.addLast(new StringDecoder());
//                        pipeline.addLast(new StringEncoder());
//                        pipeline.addLast(new CryptoEncoder());
//                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new WmsHandler());
                    }
                });
        try {
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workgroup.shutdownGracefully();
            bossgroup.shutdownGracefully();
        }
    }
}
