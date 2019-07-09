
**使用Runnable接口的优势**：

- 适合多个相同代码的线程处理同一资源的情况
- 可以避免Java单继承带来的局限
- 增强代码的健壮性，代码能够被多个线程共享，代码和数据是独立的。


**synchronized实现同步的基础**： 

Java中的每一个对象都可以作为锁。

三种表现形式：

- 对于普通同步方法，锁是当前的类对象。
- 对于静态同步方法，锁是当前类的Class对象
- 对于同步方法块，锁是Synchonized括号里配置的对象 。



**实战开发之抢票**

1. 先编写计票的Runnable子类：
```
public class TicketSalesRunnable implements Runnable{
    /**
     * 共享的属性
     */
    private  int ticketSize;
    public TicketSalesRunnable(){
        this.ticketSize=50;
    }
    @Override
    public void run() {
        for ( int i=0;i<50;++i){
            //通过持有锁，在同一时刻只有一个线程操作卖票。
            synchronized (this){
                if (ticketSize>0){
                    System.out.println(Thread.currentThread().getName() +" 卖出一张票,剩余总票数: "+(--ticketSize));
                }else{
                    System.out.println(Thread.currentThread().getName() +" 发现票卖光了，结束买票任务 ");
                    break;
                }
            }
        }
    }

    public static  TicketSalesRunnable newInstance(){
        return  new TicketSalesRunnable();
    }
}
```
客户端测试：
```
public class Client {
    public static void main(String[] args) {
       testCommonRunnable();
    }
    /**
     * 测试共享的Runnable对象
     */
    private static void testCommonRunnable() {
        TicketSalesRunnable runnable = TicketSalesRunnable.newInstance();
        new Thread(runnable, "1号卖票线程").start();
        new Thread(runnable, "2号卖票线程").start();
        new Thread(runnable, "3号卖票线程").start();
    }
    
}
```
控制台输出结果：
```
1号卖票线程 卖出一张票,剩余总票数: 49
1号卖票线程 卖出一张票,剩余总票数: 48
1号卖票线程 卖出一张票,剩余总票数: 47
//...........
2号卖票线程 卖出一张票,剩余总票数: 2
2号卖票线程 卖出一张票,剩余总票数: 1
2号卖票线程 卖出一张票,剩余总票数: 0
2号卖票线程 发现票卖光了，结束买票任务 
1号卖票线程 发现票卖光了，结束买票任务 
3号卖票线程 发现票卖光了，结束买票任务 
```