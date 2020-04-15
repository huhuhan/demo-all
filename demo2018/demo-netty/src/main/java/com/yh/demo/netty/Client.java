package com.yh.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        //服务端发送内容的TCP拆包分隔符
                       /* ByteBuf lineDelimiter = Unpooled.copiedBuffer("e".getBytes());
                        //默认换行符
                        ByteBuf[] defaultLine = Delimiters.lineDelimiter();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, defaultLine));*/
                        /**添加超时断连接*/
//                        pipeline.addLast(new ReadTimeoutHandler(50));


                        /**添加编码和解码的类*/
//                        pipeline.addLast(new StringEncoder());
//                        pipeline.addLast(new StringDecoder());
                        //JBossMarshalling编码包
//                        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
//                        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        //NIO包自带对象编码
                        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
                        pipeline.addLast(new ObjectEncoder());

                        pipeline.addLast(new ClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8379).sync();


        Scanner scanner = new Scanner(System.in);
        while (true){
            String scannerString = scanner.nextLine();
            if(scannerString.indexOf("end") != -1) {
                break;
            } else if(scannerString.indexOf("xx") != -1){
                Msg msg = new Msg();
                msg.setHeader((byte)0xa);
                msg.setLength(34);
                msg.setBody("對象body中的文本HelloWorld");
                future.channel().writeAndFlush(msg);
            } else {
//                future.channel().writeAndFlush(initMessage(msg+"\n"));
                future.channel().writeAndFlush(scannerString);
            }
        }



        future.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }

    public static ByteBuf initMessage(String msg){
        return Unpooled.copiedBuffer(msg.getBytes());
    }

}
