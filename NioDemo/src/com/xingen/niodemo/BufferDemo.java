package com.xingen.niodemo;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * author: xinGen
 * date : 2020/12/18
 * blog: https://blog.csdn.net/hexingen
 *
 * Buffer:包在一个对象内的(基本数据元素)数组，与数组比较，优点是它将数据的数据内容和信息包含在一个单一的对象中。
 */
public class BufferDemo {


    /**
     * 四个属性：
     * 1.capacity：缓冲区能够容纳的数据元素的最大数量，这个在创建时被设定，且不能被改变。
     * 2.limit：缓冲区的现存元素的计数。即第一个不能被读或者写的元素。
     * 3.position：下一个被读或者写的元素的索引。位置会自动由相应的 get( )和 put( )函数更新
     * 4.mark: 备忘位置，调用mark( )来设定mark=position。调用reset( )设定position= mark。标记在设定前是未定义的(undefined)。
     * 0 <= mark <= position <= limit <= capacity。
     */
    public void testBuffer() {
        //指定一个10容量的buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        printBufferMsg(byteBuffer);
    }

    private void printBufferMsg(Buffer buffer) {
        System.out.println(
                "创建一个容量10的byte 缓存类"
                        + " capacity： " + buffer.capacity()
                        + " , limit： " + buffer.limit()
                        + " , position: " + buffer.position());

    }
    private void printBufferContent(Buffer buffer){
        if (buffer instanceof ByteBuffer) {
            System.out.println("byte buffer的内容是：" + new String((byte[]) buffer.array()));
        }
    }

    /**
     * put():填充数据
     */
    public void testBufferPut() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        //打印hello
        byte[] helloArray = {'h', 'e', 'l', 'l', 'o'};
        for (byte c : helloArray) {
            byteBuffer.put(c);
        }
        printBufferMsg(byteBuffer);
        printBufferContent(byteBuffer);
        //指定位置替换，0位置替换成m，原始position位置设置成w。打印mellow。
        byteBuffer.put(0, (byte) 'm');
        byteBuffer.put((byte) 'w');
        printBufferMsg(byteBuffer);
        printBufferContent(byteBuffer);
        testBufferFlip(byteBuffer);
    }

    /**
     *  flip:翻转，将填充状态切换成读取状态。
     * 例如：数据已经填充满了，需要读取数据了。
     * 将当前的position位置设置成limit，position为0
     * @param byteBuffer
     */
    public void testBufferFlip(ByteBuffer byteBuffer){
        //翻转后，开始读取数据
        byteBuffer.flip();
        printBufferMsg(byteBuffer);
        //获取到边界
        int limit=byteBuffer.limit();
        byte[] byteArray=new byte[limit];
        int i=0;
        while (byteBuffer.hasRemaining()){
            byteArray[i]=byteBuffer.get();
            i++;
        }
        System.out.println(new String(byteArray));

    }

    /**
     * clear():释放
     * 将缓冲区重置成空状态。并不改变缓冲区中的任何数据元素，
     * 而将limit设置为capacity,position设置为0，mark重置。
     * @param byteBuffer
     */
    private void releaseBuffer(ByteBuffer byteBuffer){
        byteBuffer.clear();
    }

    /**
     * compact()：压缩数据，舍弃已读的数据，将未读的数据重插入到前列，重新调整缓冲区
     * position会设置成未读元素的长度
     * 与flip()结合使用
     */
    protected void compactBuffer(){
        byte[] helloArray = {'h', 'e', 'l', 'l', 'o'};
        ByteBuffer byteBuffer = ByteBuffer.wrap(helloArray);
        for (int i=0;i<2;++i){
            byteBuffer.get();
        }
        //当前position为2，还有3个未读元素
        printBufferMsg(byteBuffer);
        printBufferContent(byteBuffer);
        //压缩数据,后position为未读元素的个数，即3。
        byteBuffer.compact();
        printBufferMsg(byteBuffer);
        printBufferContent(byteBuffer);
        //翻转后继续读取数据
        testBufferFlip(byteBuffer);
    }

    /**
     * mark()：记录当前position位置, 即mark=position。
     * rest()：恢复上次的position位置, 即position=mark
     */
    public void markBuffer(){
        byte[] helloArray = {'h', 'e', 'l', 'l', 'o'};
        ByteBuffer byteBuffer = ByteBuffer.wrap(helloArray);
        //记录当前position，将mark=position，这里是记录为2
        byteBuffer.position(2).mark() .position(4);
        printBufferMsg(byteBuffer);
        //重置position位置,恢复上次记录的位置即mark，这里是恢复成2
        byteBuffer.reset();
        printBufferMsg(byteBuffer);
    }


}
