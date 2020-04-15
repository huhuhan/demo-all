package com.yh.demo.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

public class Client implements Runnable {

    private AsynchronousSocketChannel channel;

    public Client() throws IOException {
        channel = AsynchronousSocketChannel.open();
    }

    public void connect(int port) {
        channel.connect(new InetSocketAddress("127.0.0.1", port));
    }

    public void write(String data) {
        try {
            channel.write(ByteBuffer.wrap(data.getBytes())).get();
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer).get();
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String data = new String(bytes, "UTF-8").trim();
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

        }
    }

    public static void main(String[] args) throws Exception {
//        initClient();
        systemInClient();
    }

    public static void systemInClient() throws IOException {
        try {

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String inRead = scanner.nextLine();
                System.err.println(inRead + "|" + inRead.length());
                if (inRead.equals("end")) {
                    break;
                } else {
                    Client client = new Client();
                    client.connect(8379);//server2
                    Thread.sleep(500);
                    client.write(inRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步非阻塞，但链接读写不可复用
     */
    public static void initClient() {
        try {

            Client c1 = new Client();
            Client c2 = new Client();
            Client c3 = new Client();

            int port = 8379;
            c1.connect(port);
            c2.connect(port);
            c3.connect(port);

            new Thread(c1).start();
            new Thread(c2).start();
            new Thread(c3).start();

            Thread.sleep(1000);

            c1.write("c1 aaa");
            c2.write("c2 bbbb");
            c3.write("c3 ccccc");
            System.err.println("c3 write again");
            c3.write("c3 ccccc again");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
