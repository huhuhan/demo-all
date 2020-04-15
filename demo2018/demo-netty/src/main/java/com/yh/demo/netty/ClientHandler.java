package com.yh.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Msg msg = new Msg();
        msg.setHeader((byte)0xa);
        msg.setLength(34);
        msg.setBody("對象body中的文本HelloWorld");

        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if(msg instanceof String){
                String message = (String)msg;
                System.out.println("By Server Response：" + message);
            }else{
                Msg m = (Msg)msg;
                System.out.println("By Server Response：" + m.getBody());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
