package com.yh.demo.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8379);
        SocketChannel sc = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //打开通道
            sc = SocketChannel.open();
            //建立连接
            sc.connect(address);
            System.out.println("建立连接，请输出内容");
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String inRead = scanner.nextLine();
                System.out.println(inRead+"|"+inRead.length());
                if(inRead.equals("end")){
                    break;
                } else {
                    //把输入的数据放入buffer缓冲区
                    buffer.put(inRead.getBytes());
                    //复位操作
                    buffer.flip();
                    //将buffer的数据写入通道
                    sc.write(buffer);
                    //清空缓冲区中的数据
                    buffer.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(sc != null) {
                try {
                    sc.close();
                    System.in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
