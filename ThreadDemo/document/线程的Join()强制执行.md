
线程A执行任务中，需要先让线程B执行完后，再执行剩余任务。

Join():
>让调用join()方法所在的线程，停止正在执行的任务，处于等待状态。而join所属的线程，会开始强制的执行


**实战开发**


先编写一个执行任务，中途等待另外一个线程先执行的WorkThread线程：
```
public class WorkerThread extends Thread {
    private static final String TAG = WorkerThread.class.getSimpleName();

    @Override
    public void run() {

        for (int i = 0; i < 10; ++i) {
            System.out.println(TAG + " 执行 " + i);
            if (i == 2) {
                try {
                    Thread thread = new Thread(new JoinThread());
                    thread.start();
                    //会让调用join()的线程，停止正在执行的任务，处于等待状态。而join所属的线程，会开始强制的执行
                    //这里是WorkerThread线程处于等待状态 ， JoinThread会强制执行。
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
```

然后编写 中途先执行的 Runnable子类：
```
public class JoinThread implements Runnable {
    private static final String TAG=JoinThread.class.getSimpleName();
    @Override
    public void run() {
        for (int i=0;i<10;++i){
            System.out.println(TAG+" 执行 "+i);
        }
    }
}
```

客户端调用：
```
public class Client {
    public static void main(String[] args) {
         testJoinThread();
    }
    /**
     * 测试 join() 方法， 强制执行该线程
     */
    public static void testJoinThread() {
        Thread thread = new WorkerThread();
        thread.start();
    }
}
```


控制台输出结果：
```
WorkerThread 执行 0
WorkerThread 执行 1
WorkerThread 执行 2
JoinThread 执行 0
JoinThread 执行 1
JoinThread 执行 2
JoinThread 执行 3
JoinThread 执行 4
JoinThread 执行 5
JoinThread 执行 6
JoinThread 执行 7
JoinThread 执行 8
JoinThread 执行 9
WorkerThread 执行 3
WorkerThread 执行 4
WorkerThread 执行 5
WorkerThread 执行 6
WorkerThread 执行 7
WorkerThread 执行 8
WorkerThread 执行 9
```