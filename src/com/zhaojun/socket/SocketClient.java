package com.zhaojun.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @auther zhaojun0193
 * @create 2017/9/20
 */
public class SocketClient {

    public static void main(String[] args) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Socket socket = null;
        try{
            socket = new Socket("127.0.0.1",8888);

           inputStream = socket.getInputStream();

           outputStream = socket.getOutputStream();

           PrintWriter printWriter = new PrintWriter(outputStream);
           printWriter.println("hello");
           printWriter.flush();

           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
           String line = bufferedReader.readLine();

           System.out.println(line);

       }catch (IOException e){
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
               System.out.println();
           }
       }
    }
}
