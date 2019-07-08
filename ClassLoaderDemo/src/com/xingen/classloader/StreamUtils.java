package com.xingen.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by ${新根} on 2018/12/4.
 * 博客：http://blog.csdn.net/hexingen
 */
public class StreamUtils {

    /**
     *  file转成byte
     * @param file
     * @return
     */
    public static byte[] fileToByteArray(File file){
        byte[] bytes=null;
        FileInputStream fileInputStream=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        try {
            fileInputStream=new FileInputStream(file);
            FileChannel fileChannel=fileInputStream.getChannel();
            byteArrayOutputStream=new ByteArrayOutputStream();
            WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);
             ByteBuffer buffer=ByteBuffer.allocate(1024);
            while (true) {
                int i = fileChannel.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                 writableByteChannel.write(buffer);
                buffer.clear();
            }
            bytes=byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            bytes=null;
            e.printStackTrace();

        }finally {
            try {
                if (byteArrayOutputStream!=null){
                    byteArrayOutputStream.close();
                }
                if (fileInputStream!=null){
                    fileInputStream.close();
                }
            }catch (Exception e2){
            }
        }
        return bytes;
    }
}
