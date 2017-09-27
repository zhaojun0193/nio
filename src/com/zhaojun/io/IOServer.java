package com.zhaojun.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @auther zhaojun0193
 * @create 2017/9/23
 */
public class IOServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        InputStream in = null;

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",8888));
            int recvMsgSize = 0;

            byte[] recvBuf = new byte[1024];
            while (true){
                Socket socket = serverSocket.accept();
                SocketAddress socketAddress = socket.getRemoteSocketAddress();
                System.out.println("Handling client at "+socketAddress);
                in = socket.getInputStream();
                while ((recvMsgSize = in.read(recvBuf)) != -1){
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf,0,temp,0,recvMsgSize);
                    System.out.println(new String(temp));
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(serverSocket != null){
                    serverSocket.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
