package com.yh.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

public class ServerHandler2 extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.writeAndFlush(this.initString("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n"));
        ctx.write(this.initString("It is " + new Date() + " now.\r\n"));
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object request) throws Exception {
        String msg = (String)request;
        System.out.println("server record" + msg);
//        super.channelRead(ctx, msg);
        this.channelRead0(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else {
            response = "Did you say '" + request + "'?\r\n";
        }

        ChannelFuture future = ctx.write(this.initString(response));
        ctx.flush();
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }


    //当出现Throwable对象才会被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public ByteBuf initString(String value){
        return Unpooled.copiedBuffer(value.getBytes());
    }
}
