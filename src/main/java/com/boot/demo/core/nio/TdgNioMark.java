package com.boot.demo.core.nio;

import java.nio.ByteBuffer;

/**
 * @DATE 2019/11/19 15:25
 * @Description 查看nio---mark作用
 * @Author Tian Daguang
 **/
public class TdgNioMark {

    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put("yswKnight".getBytes());
        System.out.println("------开启读取模式------");
        allocate.flip();
        byte[] bytes = new byte[allocate.limit()];
        //获取缓冲区数据
        allocate.get(bytes, 0, 2);
        //mark是一个索引，通过此方法指定Buffer中一个特定的position
        System.out.println("mark 前" + allocate.position());
        allocate.mark();
        System.out.println(new String(bytes, 0, 2));
        System.out.println("第1次position:" + allocate.position());


        //这时重新获取缓冲区数据，position为4（get中获取到的第三个参数加起来的值）
        allocate.get(bytes, 3, 3);
        System.out.println(new String(bytes, 3, 3));
        System.out.println("第2次position:" + allocate.position());

        //然后可以通过调用reset()方法恢复到这个position
        allocate.reset();
        System.out.println("------重置恢复到mark位置------");
        System.out.println("第1次重置position:" + allocate.position());
        allocate.get(bytes, 0, 2);
        //mark是一个索引，通过此方法指定Buffer中一个特定的position
        System.out.println(new String(bytes, 0, 2));
        System.out.println("第1次重置 后position:" + allocate.position());

        allocate.get(bytes, 4, 2);
        System.out.println(new String(bytes, 4, 2));
        System.out.println("第1次重置 2后position:" + allocate.position());
        allocate.reset();
        System.out.println("------再次重置恢复到mark位置------");
        System.out.println("第2次重置 后position:" + allocate.position());
        allocate.get(bytes, 3, 3);
        System.out.println(new String(bytes, 3, 3));
        System.out.println("第2次重置 2后position:" + allocate.position());

        allocate.mark();
        allocate.get(bytes, 0, 2);
        System.out.println(new String(bytes, 0, 2));
        System.out.println("第二次position-----" + allocate.position());
        allocate.reset();
        System.out.println("------再次重置恢复到mark位置------");
        System.out.println(allocate.position());

    }
}
