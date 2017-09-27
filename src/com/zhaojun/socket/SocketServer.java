package com.zhaojun.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @auther zhaojun0193
 * @create 2017/9/20
 */
public class SocketServer {

    public static void main(String[] args) {


        try {
            //创建socketServer 并绑定端口
            ServerSocket socketServer = new ServerSocket();
            socketServer.bind(new InetSocketAddress("127.0.0.1",8888));
            
            while (true){
                Socket socket = socketServer.accept();
                new Thread(() -> {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = socket.getInputStream();
                        outputStream = socket.getOutputStream();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String line = bufferedReader.readLine();

                        System.out.println(line);

                        PrintWriter printWriter = new PrintWriter(outputStream);
                        printWriter.print(line +": to server");
                        printWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try{
                            if(outputStream != null){
                                outputStream.close();
                            }
                            if(inputStream != null){
                                inputStream.close();
                            }
                            if(socket != null){
                                socket.close();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
