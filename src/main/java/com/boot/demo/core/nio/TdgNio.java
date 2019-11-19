package com.boot.demo.core.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @DATE 2019/11/19 14:15
 * @Description NIO学习
 * @Author Tian Daguang
 * <p>
 * 总结出使用Buffer一般遵循下面几个步骤：
 * <p>
 * 分配空间（ByteBuffer buf = ByteBuffer.allocate(1024); 还有一种allocateDirector后面再陈述）
 * <p>
 * 写入数据到Buffer(int bytesRead = fileChannel.read(buf);)
 * <p>
 * 调用filp()方法（ buf.flip();）
 * <p>
 * 从Buffer中读取数据（System.out.print((char)buf.get());）
 * <p>
 * 调用clear()方法或者compact()方法
 **/
public class TdgNio {


    /**
     * 功能描述: nio读取字符串 然后
     * <p>
     * ByteBuffer
     * <p>
     * <p>   读写从buffer角度来说
     * flip    从写到读
     * <p>
     * clear   从读到写
     * compact 从读到写 ,不清空buffer
     *
     * @return : void
     * @param: str     长度为4整数  如果兼容 需要对 不是4截取的做单独处理
     * @param: fileName
     * @author : Tiandaguang
     * @date : 2019/11/19 16:06
     */
    public static void readStrAndWrite(String str, String fileName) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        //使用buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int num = 0;
        if (str.length() % 4 == 0) {
            num = str.length() / 4;
        } else {
            num = str.length() / 4 + 1;
        }
        try {
            RandomAccessFile bFile = new RandomAccessFile(fileName, "rw");
            for (int i = 0; i < num; i++) {
                String newStr = str.substring(i * (4), (i + 1) * 4);
                System.out.println(newStr);
                //清空buffer
                buffer.put(newStr.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    bFile.write((char) buffer.get());
                }
                buffer.compact();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readStrAndWrite("1234432112344321", "E:/eee.txt");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        RandomAccessFile aFile = null;
        RandomAccessFile bFile = null;
        try {
            aFile = new RandomAccessFile("D:/nio.txt", "rw");
            bFile = new RandomAccessFile("D:/aa.txt", "rw");

            FileChannel channel = aFile.getChannel();

            int read = channel.read(buffer);
            while (read != -1) {
//                System.out.println("flip前limit-->" + buffer.limit());
//                System.out.println("flip前position-->" + buffer.position());
                buffer.flip();
//                System.out.println("flip后limit-->" + buffer.limit());
//                System.out.println("flip后position-->" + buffer.position());
                if (buffer.hasRemaining()) {
                    bFile.write((char) buffer.get());
                }

//                System.out.println("compact前limit-->" + buffer.limit());
//                System.out.println("compact前position-->" + buffer.position());
                buffer.compact();
//                System.out.println("compact后 limit-->" + buffer.limit());
//                System.out.println("compact后 position-->" + buffer.position());
                read = channel.read(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!buffer.hasRemaining()) {
                    buffer = null;
                }
                if (aFile != null) {
                    aFile.close();
                }
                if (bFile != null) {
                    bFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
