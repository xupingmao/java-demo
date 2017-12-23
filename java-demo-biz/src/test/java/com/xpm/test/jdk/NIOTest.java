package com.xpm.test.jdk;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

/**
 * Created by xupingmao on 2017/9/8.
 */
public class NIOTest {

    @Test
    public void writeBuffer() throws IOException {
        File file = new File("test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel channel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello,world".getBytes());
        channel.write(byteBuffer);
        channel.close();
        fos.close();
    }

    @Test
    public void readBuffer() throws IOException {
        File file = new File("test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer);


        System.out.println(byteBuffer);
        System.out.println(new String (byteBuffer.array(), 0,10));
        fis.close();
    }

}
