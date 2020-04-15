package com.yh.demo.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //默认方式转ByteBuf
        /*ByteBuf buf = (ByteBuf) msg;
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        String request = new String(data, "utf-8");*/

        //初始化配置加入编码
        String request = (String) msg;
        System.out.println("By Client Response: " + request);
        //写给客户端
        String response = "服务反馈的信息";
        /*Scanner scanner = new Scanner(System.in);
        response = scanner.nextLine();
        System.in.close();*/
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes("utf-8")));
        //响应客户端后关闭链接
//        .addListener(ChannelFutureListener.CLOSE);
    }


    //当出现Throwable对象才会被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
