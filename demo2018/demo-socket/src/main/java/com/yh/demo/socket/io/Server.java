package com.yh.demo.socket.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {
    private static int PORT = 8379;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务器端启动了....");
            //进行阻塞
            Socket socket = serverSocket.accept();
            //启动一个线程来处理客户端请求
//            new Thread(new ServerHandler(socket)).start();

            //通过线程池启动，启动一个线程来处理客户端请求
            HandlerExecutorPool pool = new HandlerExecutorPool(5, 3);
            while (true) {
                socket = serverSocket.accept();
                pool.execute(new ServerHandler(socket));
                System.out.println("1");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket = null;
        }
    }
}

class HandlerExecutorPool {
    private ExecutorService executor;

    public HandlerExecutorPool(int maxPoolSize, int queueSize) {
        System.out.println("2");
        this.executor = Executors.newSingleThreadExecutor();
//        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        this.executor.execute(task);
    }

}