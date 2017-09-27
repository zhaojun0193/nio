package com.zhaojun;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * @auther zhaojun0193
 * @create 2017/9/19
 */
public class NioTest {

    @Test
    public void testInputStream(){
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream("E:\\workspace\\idea\\nio\\resource\\source.txt"));
            byte[] buf = new byte[24];
            int read = in.read(buf);
            while (read != -1){
                for(int i = 0;i<read; i++){
                    System.out.print((char)buf[i]);
                }
                read = in.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testNIO(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("E:\\workspace\\idea\\nio\\resource\\source.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(20);

            int read = fileChannel.read(buffer);
            System.out.println(read);

            while (read != -1){
                buffer.flip();
                while (buffer.hasRemaining()){
                    System.out.print((char)buffer.get());
                }
//                buffer.compact();
                buffer.clear();
                read = fileChannel.read(buffer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @Test
    public void inputBufferTest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\workspace\\idea\\nio\\resource\\source.txt")));
        StringBuffer buffer = new StringBuffer();
        int ch;
        while ((ch = bufferedReader.read())!= -1){
            buffer.append((char)ch);
        }
        System.out.println(buffer);
        bufferedReader.close();
    }

    @Test
    public void testNIOInputStream()  {
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream("E:\\workspace\\idea\\nio\\resource\\source.txt");
            FileChannel channel = inputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int read = channel.read(byteBuffer);

            while (read != -1){
                byteBuffer.flip();
                System.out.println();
                while (byteBuffer.hasRemaining()){
                    System.out.print((char)byteBuffer.get());
                }
                byteBuffer.compact();
                read = channel.read(byteBuffer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
           try{
               if(inputStream != null){
                   inputStream.close();
               }
           }catch (IOException e){
               e.printStackTrace();
           }
        }

    }

    @Test
    public void hhTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
//        Thread.sleep(2000);
        System.out.println(2222);
        A.a.world();

        System.out.println(Color.Red.toString());

        A.a.print();
        A.b.print();
    }
}
