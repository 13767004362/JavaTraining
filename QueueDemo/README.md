#  **Queue队列**

**前言**：

>队列是一种比较特殊的线性结构,只支持FIFO,头部删除，尾部添加,先进先出。

Queue接口与List、Set同一级别,都是继承了Collection接口。LinkedList是实现了Deque接口。
```
public interface Queue<E> extends Collection<E> {
    boolean add(E var1);

    boolean offer(E var1);

    E remove();

    E poll();

    E element();

    E peek();
}

```

![image](https://github.com/13767004362/JavaTraining/blob/master/QueueDemo/document/Queu.png)

Queue队列大体分为以下两种：

- 非阻塞队列,以ConcurrentLinkedQueue为代表的高性能队列非阻塞。
- 阻塞队列(BlockingQueue接口为代表):当队列中为空,从队列获取元素的操作会被阻塞,而执行所在的线程也随着被阻塞,直到其他的线程往队列中插入新的元素。同样,试图往已经满了的阻塞队列中添加新元素的操作也会被阻塞，直到其他的线程取走队列中的元素。


队列的常用API:


| api | 描述 |
| :---|--- |
| `offer()` | 添加一个元素到队列,成功返回true。若是队列已经满,返回false。 |
| `poll()` | 移除且返回队列头部的元素。若队列为空,则返回null |
| `peek()` | 返回队列头部的元素。如果队列为空，则返回null |
| `put()` | 添加一个元素到尾部。若是队列已满，则会阻塞调用线程,直到有元素被移除才插入返回 |
| `take()` | 移除且返回队列的头部元素。若是队列为空,会阻塞调用的线程,直到有新的元素添加才返回 |


注意点：`aad()`、`remove()`和`element()`操作在你试图为一个已满的队列增加元素或从空队列取得元素时 抛出异常。



###  **非阻塞队列**

非阻塞队列可以用循环CAS的方式来保证数据的一致性，来达到线程安全的目的。

内置的两种非阻塞队列类：

- PriorityQueue： 类实质上维护了一个有序列表。加入到 Queue 中的元素根据它们的天然排序（通过其 java.util.Comparable 实现）或者根据传递给构造函数的 java.util.Comparator 实现来定位。
- ConcurrentLinkedQueue：是基于链接节点的、线程安全的队列。并发访问不需要同步。因为它在队列的尾部添加元素并从头部删除它们，所以只要不需要知道队列的大小，ConcurrentLinkedQueue 对公共集合的共享访问就可以工作得很好。收集关于队列大小的信息会很慢，需要遍历队列。



多线程共享访问一个公共Collection时候,CuoncurrentLinkedQueue是一个恰当的选择。

CuoncurrentLinkedQueue类：
>是一个适用于高并发场景下的队列，通过无锁的方式，实现
了高并发状态下的高性能，通常ConcurrentLinkedQueue性能好于BlockingQueue.它
是一个基于链接节点的无界线程安全队列。该队列的元素遵循先进先出的原则。头是最先
加入的，尾是最近加入的，该队列不允许null元素

**实战案例**

构建一个多人取票的场景。


```
public class TicketQueue {

    private Queue<Integer> queue;
    private CountDownLatch countDownLatch;
    private final static int TICKET_SIZE = 10;

    private TicketQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
        this.countDownLatch = new CountDownLatch(TICKET_SIZE);
    }

    public static TicketQueue create() {
        return new TicketQueue();
    }

    /**
     * 生产
     */
    public void product() {
        for (int i = 1; i <= TICKET_SIZE; ++i) {
            queue.offer(i);
        }
    }

    /**
     * 消费
     */
    public void consume(final  String mark) throws  InterruptedException{
        while (!this.queue.isEmpty()) {
            Integer ticket = queue.poll();
            if (ticket != null) {
                System.out.println(mark+" ,从队列中取出的票是 " + ticket);
                this.countDownLatch.countDown();
                //Thread.sleep(100);
            }
        }
    }

    /**
     * 等待，当计数减到0时，所在线程才执行
     */
    public void await() {
        try {
            this.countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

构建消费者线程：
```
    private static class ConsumeThread implements Runnable {
        private TicketQueue ticketQueue;
        private String mark;

        public ConsumeThread(TicketQueue ticketQueue,String mark) {
            this.ticketQueue = ticketQueue;
            this.mark=mark;
        }

        @Override
        public void run() {
            try {
                this.ticketQueue.consume(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

测试：
```
    public static void test() {
        TicketQueue ticketQueue = TicketQueue.create();
        // 主线程调用，充当生产者线程，先生产10张票
        ticketQueue.product();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // 构建三个消费者线程
        final  int product_size=3;
        for (int i=0;i<product_size;++i){
            executorService.submit(new ConsumeThread(ticketQueue,"消费者线程"+(i+1)));
        }
        // 等待消费完后，关闭线程池
        ticketQueue.await();
        executorService.shutdown();
    }
```

```
消费者线程1 ,从队列中取出的票是 1
消费者线程3 ,从队列中取出的票是 3
消费者线程2 ,从队列中取出的票是 2
消费者线程3 ,从队列中取出的票是 5
消费者线程3 ,从队列中取出的票是 7
消费者线程3 ,从队列中取出的票是 8
消费者线程1 ,从队列中取出的票是 4
消费者线程3 ,从队列中取出的票是 9
消费者线程2 ,从队列中取出的票是 6
消费者线程1 ,从队列中取出的票是 10
```



###  **阻塞队列**

java.util.concurrent中添加了BlockingQueue接口和5个阻塞队列类。在队列中添加或者删除元素时，线程执行操作阻塞，直到有空间或者元素可用。

| 阻塞队列类 | 描述 |
| :---| :--- |
| LinkedBlockingQueue | 一个由链接节点支持的可选有界队列 |
| ArrayBlockingQueue | 一个由数组支持的有界队列 |
| PriorityBlockingQueue | 一个由优先级堆支持的无界优先队列 |
| DelayQueue | 一个由优先级堆支持的、基于时间的调度队列 |
| SynchronnousQueue | 一个利用BlockingQueue捷库的简单rendezvous聚集机制 |


实战案例：

定义一个商品类：
```
    /**
     * 产品
     */
    public static class Product {
        private String money;

        public static Product create() {
            return new Product();
        }

        public Product setMoney(String money) {
            this.money = money;
            return this;
        }
    }
```
定义一个WorkQueue,进行插入尾部,取出头部的操作：
```
public class WorkQueue {
    public static WorkQueue create() {
        return new WorkQueue();
    }

    private BlockingQueue<Product> queue;
    private volatile boolean isStop;

    private WorkQueue() {
        this.queue = new LinkedBlockingQueue();
    }

    /**
     * 从阻塞队列中生产
     *
     * @throws InterruptedException
     */
    public void produce() throws InterruptedException {
        Thread.sleep(500);
        if (!isStop) {
            int random = new Random().nextInt(100) + 1;
            Product product = Product.create().setMoney(random + "元");
            this.queue.put(product);
            System.out.println("插入元素:-->往阻塞队列中添加,进行生产" + random + "元的商品");
        }
    }

    /**
     * 从阻塞中队列中获取
     *
     * @throws InterruptedException
     */
    public void consume() throws InterruptedException {
        long start_time = System.currentTimeMillis();
        Product product = this.queue.take();
        long end_time = System.currentTimeMillis();
        System.out.println(new StringBuffer()
                .append("获取元素:-->从阻塞队列中，进行消费 ")
                .append(product.money)
                .append(" ,阻塞等待时间  ")
                .append(end_time - start_time)
                .append(" 毫秒"));
    }

    public boolean isStop() {
        return isStop;
    }
}

```
定义一个生产者线程：
```
    private static class ProductThread implements Runnable {
        private WorkQueue workQueue;

        public ProductThread(WorkQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            try {
                while (!workQueue.isStop) {
                    workQueue.produce();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

```
定义一个消费者线程:
```

    private static class ConsumeThread implements Runnable {
        private WorkQueue workQueue;

        public ConsumeThread(WorkQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            try {
                while (!workQueue.isStop) {
                    workQueue.consume();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

测试如下：
```Java
    public static void test() {
        WorkQueue workQueue = WorkQueue.create();
        new Thread(new ProductThread(workQueue)).start();
        new Thread(new ConsumeThread(workQueue)).start();
        try {
            Thread.sleep(2000);
            workQueue.isStop = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

输出结果：
```
插入元素:-->往阻塞队列中添加,进行生产3元的商品
获取元素:-->从阻塞队列中，进行消费 3元 ,阻塞等待时间  501 毫秒
插入元素:-->往阻塞队列中添加,进行生产12元的商品
获取元素:-->从阻塞队列中，进行消费 12元 ,阻塞等待时间  500 毫秒
插入元素:-->往阻塞队列中添加,进行生产68元的商品
获取元素:-->从阻塞队列中，进行消费 68元 ,阻塞等待时间  501 毫秒
```

---


队列常用场景,归纳总结：

LinkedBlockingQueue 多用于任务队列。
ConcurrentLinkedQueue  多用于消息队列。

| 场景 | 对应的队列 |
| :---| :--- |
| 单生产者，单消费者 | 用 LinkedBlockingqueue |
| 多生产者，单消费者 | 用 LinkedBlockingqueue |
| 单生产者 ，多消费者| 用 ConcurrentLinkedQueue |
| 多生产者 ，多消费者 | 用 ConcurrentLinkedQueue |


 
   
  
   

