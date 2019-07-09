
yield()
>使当前线程从执行状态（运行状态）变为可执行态（就绪状态）。cpu会从众多的可执行态里选择，也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的，并不是说一定会执行其他线程而该线程在下一次中不会执行到了。


**实战开发**：

编写一个执行输入任务，执行到i=15时候，礼让释放cpu的线程
```
public class YieldThread extends Thread{
    private static final String TAG=YieldThread.class.getSimpleName();
    @Override
    public void run() {
        for (int i=0;i<30;++i){
            System.out.println(Thread.currentThread().getName()+" 执行 "+i);
            // 当i为15时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i==15){
                Thread.currentThread().yield();//线程礼让
            }
        }
    }
    public static  YieldThread newInstance(){
        return new YieldThread();
    }
}
```
客户端测试：
```
public class Client {
    public static void main(String[] args) {
        testYieldThread();
    }

    /**
     * yield()线程的礼让
     */
    public static void testYieldThread() {
        YieldThread yieldThread1 = YieldThread.newInstance();
        yieldThread1.setName(" WorkerThread_1 ");
        YieldThread yieldThread2 = YieldThread.newInstance();
        yieldThread2.setName(" WorkerThread_2 ");
        yieldThread1.start();
        yieldThread2.start();
    }
}
```

控制台输出结果：
```
 WorkerThread_1  执行 0
 WorkerThread_1  执行 1
 WorkerThread_1  执行 2
 WorkerThread_1  执行 3
 WorkerThread_1  执行 4
 WorkerThread_1  执行 5
 WorkerThread_1  执行 6
 WorkerThread_1  执行 7
 WorkerThread_1  执行 8
 WorkerThread_1  执行 9
 WorkerThread_1  执行 10
 WorkerThread_1  执行 11
 WorkerThread_1  执行 12
 WorkerThread_1  执行 13
 WorkerThread_1  执行 14
 WorkerThread_1  执行 15
 WorkerThread_2  执行 0
 WorkerThread_2  执行 1
 WorkerThread_2  执行 2
 WorkerThread_2  执行 3
 WorkerThread_2  执行 4
 WorkerThread_2  执行 5
 WorkerThread_2  执行 6
 WorkerThread_2  执行 7
 WorkerThread_2  执行 8
 WorkerThread_2  执行 9
 WorkerThread_2  执行 10
 WorkerThread_2  执行 11
 WorkerThread_2  执行 12
 WorkerThread_2  执行 13
 WorkerThread_2  执行 14
 WorkerThread_2  执行 15
 WorkerThread_2  执行 16
 WorkerThread_2  执行 17
 WorkerThread_2  执行 18
 WorkerThread_2  执行 19
 WorkerThread_2  执行 20
 WorkerThread_2  执行 21
 WorkerThread_2  执行 22
 WorkerThread_2  执行 23
 WorkerThread_2  执行 24
 WorkerThread_2  执行 25
 WorkerThread_2  执行 26
 WorkerThread_2  执行 27
 WorkerThread_2  执行 28
 WorkerThread_2  执行 29
 WorkerThread_1  执行 16
 WorkerThread_1  执行 17
 WorkerThread_1  执行 18
 WorkerThread_1  执行 19
 WorkerThread_1  执行 20
 WorkerThread_1  执行 21
 WorkerThread_1  执行 22
 WorkerThread_1  执行 23
 WorkerThread_1  执行 24
 WorkerThread_1  执行 25
 WorkerThread_1  执行 26
 WorkerThread_1  执行 27
 WorkerThread_1  执行 28
 WorkerThread_1  执行 29
```