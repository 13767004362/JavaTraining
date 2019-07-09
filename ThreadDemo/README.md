## JAVA Thread（线程）

**线程简介：**

**线程**：
     
概念：一个运行程勋中的某一个功能模块执行分支，即线程是进程的某一分支。
     
特性：独立的、并发的执行流
     
**多线程**：
     
单线程的程序如同一个人的餐厅，只能一件事做完后才能做下一件事情。多线程的程序如同多人的餐厅，可以同时做多件事情。

**进程**：
    
概念：处于运行中过程中的程序。
    
特性：独立性（含有独立空间资源）、动态性（含有生命周期）、并发性（多个运行，互不干扰）

操作系统中可以同时执行多个任务，每个任务是进程，进程可以同时执行多个任务，每个任务就是线程。

**创建线程的方式：**
  
1. 继承Thread,创建Thread子类。2. 实现Runnable接口。
  
两种方式的比较：
      
- Thread子类方式：继承的局限性，每次都是new 线程对象，不同对象间资源不能共享，故不能资源共享。

- Runnable实现类方式：
       
       1. 适合多个相同的代码程序去处理同一资源的情况
       
       2. 可以避免java单继承性的局限性
       
       3. 增强代码的健壮性，代码能被多个线程共享。代码与数据是独立的。
       
实际开发中使用方式：推荐使用Runnable接口实现多线程。
    
**线程的状态：**
   
- 创建： new出线程对象
- 就绪： 调用start(),线程进入就绪状态。线程添加到线程队列中排队，等待获取cpu
- 运行状态：  获取到cpu的线程，进入运行状态，执行run()中代码。

- 堵塞状态： 某个运行状态的线程，因特些特殊原因（如人为挂起，执行耗时的输入和输出操作），将cpu让出，且中断正在执行的操作，这时候便进入阻塞状态。 在运行状态中，调用sleep(),suspend(),wait()等方法，线程会进入阻塞状态。阻塞状态的线程不会添加到线程队列中，只有消除阻塞原因后，线程转变成就绪状态。
- 死亡状态： 调用stop()或者执行完run()中代码后，线程处于死亡状态。

线程的生命周期如下图所示：
![image](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.png)

**线程中常用的API:**
   
- `currentThread()` : 返回目前正在执行的线程对象

- `isInterrupted()` : 判断目前线程是否被中断。若是，返回ture

- `isAlive()` : 判断线程是否在活动，若是，返回true

- `join(long millis)`:强制执行millis毫秒后，线程死亡。调用该方法，能让调用它的线程强制执行，该线程运行期间，其他线程不能执行。

- `setPriority()`:设置线程的优先级

- `sleep(long millis)`:使正在执行的线程，休眠millis毫秒

- `yeild()`:将目前正在执行的线程暂停，允许其他线程执行。

**线程各种执行动作：**
   
1. 线程的强制执行：调用join()
   
2. 线程的休眠：调用sleep()让线程暂时的休眠，实现延迟的效果。
   
3. 线程的中断：调用interrruput()让线程中断运行状态。
   
4. 后台线程：只要还有一个前台的线程在执行，运用程序的进程就不会消失。若是没有前台线程，进程会结束。
   
5. 线程的优先级：并非优先级高的线程一定就会执行，哪个线程先执行，将由cpu调度决定。主线程的优先级为中级。
   
6. 线程的礼让：当前线程调用yield()方法时，会进入就绪状态，让位于优先级高于或等于的其他线程。与sleep()方法的区别：不会阻塞线程。



**线程的同步：**
   
原因：

一个程序中，多线程若是通过同一个runnable接口的实现的。这则意味着runnale接口的实现类中的属性将被多个线程对象共享。这便会造成一种问题，若是多个线程要操作同一资源时，就会产生资源同步的问题。

解决方式：使用同步监视器
     
1. 同步代码块：通常将共享资源当做同步监视器。 注意点：任何时刻只有一个线程可以获得同步监视器的锁定，当同步代码块执行完后，线程会释放锁
     
2. 同步方法：使用synchronized关键字来修饰方法。同步方法的监视器是this，也就是调用该方法的对象。
  
同步的含义：
    
多个操作在同一时刻段内只能有一个线程进行，其他线程要等待从线程完成之后才能继续执行。


**线程的死锁：** 
   
 原因：两个线程都是等待彼此先完成，造成程序上的停歇。
   
**传统的线程通讯：**
   
1. `wait()`：使线程等待，直到该同步监视器的notify()或notifyAll（）唤醒该线程
  
2. `notify()`：唤醒此同步监视器中的一个线程，是任意选择的
   
3. `notifyAll`：唤醒此同步监视器的所用线程，但是只用当前线程释放锁后才能执行被唤醒的线程

**注意点**： wait()、notify()、notifyAll()需要通过监视器对象使用，属于obeject类。同组的 wait() 只能被同组的notify（）唤醒。在根类Object中是用final关键字修饰，且仅在synchronized修饰块中被调用。

**系统上同时执行多个程序的解释：**
   
在某一时刻，cpu只能执行一个进程（即程序）。但是可以快速切换程序，达到同时运行程序的效果。


接下来，通过若干案例加深理解。


| 案例 | 描述 |
| :--- | :--- |
| [线程的Sleep()沉睡](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84Sleep()%E6%B2%89%E7%9D%A1.md) | 介绍线程的沉睡 |
| [线程的interrupt()中断](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84interrupt()%E4%B8%AD%E6%96%AD.md) | 介绍线程的停止 |
| [线程的yield()礼让](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84yield()%E7%A4%BC%E8%AE%A9.md) | 介绍线程的礼让 |
| [线程的Join()强制执行](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84Join()%E5%BC%BA%E5%88%B6%E6%89%A7%E8%A1%8C.md) | 介绍线程的强制执行,停止其余线程 |
| [共享Runnable接口与Synchronized锁](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E5%85%B1%E4%BA%ABRunnable%E6%8E%A5%E5%8F%A3%E4%B8%8ESynchronized%E9%94%81.md) | 介绍如何实现买票程序 |
| [线程的volatile锁](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84volatile%E9%94%81.md) | 介绍线程的volatitle锁 |
| [ThreadLocal线程变量](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/ThreadLocal%E7%BA%BF%E7%A8%8B%E5%8F%98%E9%87%8F.md) | 介绍线程变量 |
| [经典案例：线程的生产者与消费者模式](https://github.com/13767004362/JavaTraining/blob/master/ThreadDemo/document/%E7%BA%BF%E7%A8%8B%E7%9A%84%E7%94%9F%E4%BA%A7%E8%80%85%E4%B8%8E%E6%B6%88%E8%B4%B9%E8%80%85%E6%A8%A1%E5%BC%8F.md) | 介绍线程的同步与唤醒机制 |





        
       