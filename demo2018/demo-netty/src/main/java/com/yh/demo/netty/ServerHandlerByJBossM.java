package com.yh.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandlerByJBossM extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof String){
            System.out.println("By Client Response: " + msg);
        }else{
            Msg m = (Msg)msg;
            System.out.println("By Client Response: " + m.toString());
//            m.setBody("人生苦短，快用python");
//            ctx.writeAndFlush(m);
        }
//        String response = "服务反馈的信息";
        Msg response = new Msg();
        response.setHeader((byte)0xa);
        response.setLength(34);
        response.setBody("服务反馈的信息实体类");
        ctx.writeAndFlush(response);
    }


    //当出现Throwable对象才会被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
