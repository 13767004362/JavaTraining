
前言：
>java5之后，并发包中新增了Lock接口（以及相关实现类）用来实现锁的功能，它提供了与synchronized关键字类似的同步功能。既然有了synchronized这种内置的锁功能，为何要新增Lock接口？先来想象一个场景：手把手的进行锁获取和释放，先获得锁A，然后再获取锁B，当获取锁B后释放锁A同时获取锁C，当锁C获取后，再释放锁B同时获取锁D，以此类推，这种场景下，synchronized关键字就不那么容易实现了，而使用Lock却显得容易许多
使用



**synchronized和ReentrantLock的比较**:

 1. Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
2. synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
3. Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
4. 通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
5. Lock可以提高多个线程进行读操作的效率。

**ReentrantLock 常用API**

| 常用的方法 | 描述 |
| :---| :--- |
| void lock() | 执行此方法时，如果锁处于空闲状态，当前线程将获取到锁。相反，如果锁已经被其他线程持有，将禁用当前线程，直到当前线程获取到锁。 |
| boolean tryLock() | 如果锁可用，则获取锁，并立即返回true，否则返回false. 该方法和lock()的区别在于，tryLock()只是"试图"获取锁，如果锁不可用，不会导致当前线程被禁用，当前线程仍然继续往下执行代码。而lock()方法则是一定要获取到锁，如果锁不可用，就一直等待，在未获得锁之前,当前线程并不继续向下执行 |
| void unlock() |  执行此方法时，当前线程将释放持有的锁. 锁只能由持有者释放，如果线程并不持有锁，却执行该方法，可能导致异常的发生.|
| Condition newCondition() | 条件对象，获取等待通知组件。该组件和当前的锁绑定，当前线程只有获取了锁，才能调用该组件的await()方法，而调用后，当前线程将缩放锁。 |


### **使用ReentrantLock构建生产者与消费者模式**


ReentrantLock是独占锁，同一时刻只有一个线程能获取到锁。


每一个Lock可以有任意数据的Condition对象，Condition是与Lock绑定的，所以就有Lock的公平性特性：如果是公平锁，线程为按照FIFO的顺序从Condition.await中释放，如果是非公平锁，那么后续的锁竞争就不保证FIFO顺序了。

Condition接口定义的方法，await对应于Object.wait，signal对应于Object.notify，signalAll对应于Object.notifyAll。特别说明的是Condition的接口改变名称就是为了避免与Object中的wait/notify/notifyAll的语义和使用上混淆。


构建生产与消费的过程，采用等待与唤醒机制：

```java

    private static class ProductHandler {
        //控制锁
        private final Lock lock = new ReentrantLock();
        // 生产的条件
        private Condition productCondition = lock.newCondition();
        // 消费的条件
        private Condition consumerCondition = lock.newCondition();
        //产品
        private volatile String product;

        public void consumer() {
            lock.lock();
            try {
                if (product == null) {
                    // 产品还未生产，需要等待
                    this.consumerCondition.await();
                }
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + " ，消费了产品:" + product);
                this.product = null;
                // 消费完后，通知生产线程进行生产
                this.productCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void product(String p) {
            lock.lock();
            try {
                if (product != null) {
                    // 产品还未消费，需要等待
                    productCondition.await();
                }
                Thread.sleep(50);
                this.product = p;
                System.out.println(Thread.currentThread().getName() + " ,生产了产品:" + product);
                // 生产好产品后，通知消费线程进行消费
                this.consumerCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }
```
定义生产线程：
```java
    private static class ProductThread extends Thread {
        private static final String TAG = ProductThread.class.getSimpleName();
        private ProductHandler handler;

        public ProductThread(ProductHandler handler) {
            super("生产线程-->" + TAG);
            this.handler = handler;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; ++i) {
                handler.product("" + i);
            }
        }
    }
```
定义消费线程：
```java
 private static class ConsumerThread extends Thread {
        private static final String TAG = ConsumerThread.class.getSimpleName();
        private ProductHandler handler;

        public ConsumerThread(ProductHandler handler) {
            super("消费线程-->" + TAG);
            this.handler = handler;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; ++i) {
                handler.consumer();
            }
        }
    }
```
测试：
```java
    public static void test() {
        ProductHandler productHandler = new ProductHandler();
        new ConsumerThread(productHandler).start();
        new ProductThread(productHandler).start();
    }
```
输出结果：
```
生产线程-->ProductThread ,生产了产品:1
消费线程-->ConsumerThread ，消费了产品:1
生产线程-->ProductThread ,生产了产品:2
消费线程-->ConsumerThread ，消费了产品:2
生产线程-->ProductThread ,生产了产品:3
消费线程-->ConsumerThread ，消费了产品:3
```
### **使用ReentrantReadWriteLock提高读写效率**

与synchronized相比较,采用ReadWriteLock,提高读写效率，读与读不互斥，写与写互斥，读与写互斥。


采用ReentrantReadWriteLock来产生读写锁,分别在读取数据方法和写入数据方法中进行锁定控制,如下所示：

```java
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

```
定义写入线程：

```java
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

```

定义读取线程：

```java
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
```

测试：

```java
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
```
输出结果
```
 写入线程-->WriteThread2 准备写入数据 
 写入线程-->WriteThread2 写入数据 2
 写入线程-->WriteThread1 准备写入数据 
 写入线程-->WriteThread1 写入数据 1
 写入线程-->WriteThread3 准备写入数据 
 写入线程-->WriteThread3 写入数据 3
 读取线程-->ReadThread1 准备开始读取数据 
 读取线程-->ReadThread2 准备开始读取数据 
 读取线程-->ReadThread3 准备开始读取数据 
 读取线程-->ReadThread3 读取数据是 3
 读取线程-->ReadThread2 读取数据是 3
 读取线程-->ReadThread1 读取数据是 3
```
从上可见：一个写入线程在写，其他写入线程无法操作，读的线程也无法操作。
在一个读取线程在读取，其他的读取线程也可以进行读取。

