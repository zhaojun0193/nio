package com.zhaojun.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @auther zhaojun0193
 * @create 2017/9/23
 */
public class NioClient {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = null;

        try{
            channel = SocketChannel.open();
            //开启异步
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("localhost",8888));

            if(channel.finishConnect()){
                int i = 0;
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();

                    buffer.put(info.getBytes());
                    buffer.flip();

                    while (buffer.hasRemaining()){
                        System.out.println(buffer);
                        channel.write(buffer);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if(channel != null){
                    channel.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
