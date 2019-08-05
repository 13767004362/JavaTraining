package com.xingen.threaddemo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * author: xinGen
 * date : 2019/8/5
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * <p>
 * 提高读写效率，读与读不互斥，写与写互斥，读与写互斥。
 */
public class WriteReadSample {

    public static void test() {
        Buffer buffer=new Buffer();
        int size=3;
        for (int i=1;i<=size;++i){
            // 模拟多个线程去写，看是否互斥
            new WriteThread(buffer,i).start();
        }
        for (int i=1;i<=size;++i){
             // 模拟多个线程同时读，看是否互斥
             new ReadThread(buffer,i).start();
        }
    }
    private static class ReadThread extends Thread{
        private static final String TAG=ReadThread.class.getSimpleName();
        private Buffer buffer;
        public ReadThread(Buffer buffer,int i) {
            super(" 读取线程-->"+TAG+i);
            this.buffer = buffer;
        }
        @Override
        public void run() {
              this.buffer.readData();
        }
    }

    private static class  WriteThread extends  Thread{
        private static final String TAG=WriteThread.class.getSimpleName();
        private Buffer buffer;
        private int data;
        public WriteThread(Buffer buffer,int data) {
            super(" 写入线程-->"+TAG+data);
            this.buffer = buffer;
            this.data=data;
        }
        @Override
        public void run() {
            this.buffer.writeData(data);
        }
    }
    private static class Buffer {
        private int data;
        // 读写互斥锁
        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void readData() {
            Lock readLock = readWriteLock.readLock();
            try {
                // 开始锁定
                readLock.lock();
                Thread currentThread = Thread.currentThread();
                System.out.println(currentThread.getName() + " 准备开始读取数据 ");
                Thread.sleep(50);
                System.out.println(currentThread.getName() + " 读取数据是 " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                readLock.unlock();
            }
        }

        public void writeData(int data) {
            Lock writeLock = readWriteLock.writeLock();
            try {
                writeLock.lock();
                Thread currentThread = Thread.currentThread();
                System.out.println(currentThread.getName() + " 准备写入数据 ");
                Thread.sleep(50);
                this.data = data;
                System.out.println(currentThread.getName() + " 写入数据 " + this.data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }
}
