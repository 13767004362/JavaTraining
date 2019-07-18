package com.xingen.sixprinciples;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 *
 * 单一原则
 */
public class SinglePrinciples {


    public  static void  test(){
        // 构建一个数据操作者
        DataOperator operator=new DataOperator();
        // 添加监听器
        operator.query(new DataListener() {
            @Override
            public void onChange(String content) {
                System.out.println(content);
                //不再使用，释放资源
                operator.release();
            }
        });
    }

    /**
     *  定义一个数据监听器
     */
    public interface  DataListener{
        void onChange(String content);
    }
    /**
     * 定义一个数据操作者
     */
    private  static  class  DataOperator{
        // 监控数据的线程池
        private ExecutorService executor;
        private DataMonitor dataCache;
        public DataOperator() {
          this.executor  = Executors.newSingleThreadExecutor();
          this.dataCache=new DataMonitor();
        }
        public void query(DataListener listener){
             dataCache.addListener(listener);
             // 模拟异步线程，监控数据
            executor.execute(new QueryThread(dataCache));
        }
        public void release(){
            executor.shutdown();
            dataCache.release();
        }
    }

    /**
     *  定义一个专门监听数据变化的Runnable子类
     */
    private static class QueryThread implements  Runnable{
        private DataMonitor dataMonitor;
        public QueryThread(DataMonitor dataMonitor) {
            this.dataMonitor = dataMonitor;
        }

        @Override
        public void run() {
             try {
                 Thread.sleep(1000);
                 dataMonitor.notifyData(" 一秒后，监听到数据发生变化");
             }catch (Exception e){
                 e.printStackTrace();
             }
        }
    }
    /**
     *  定义一个类，专门缓存监听器,通知数据变化
     */
    private static class DataMonitor {
        private List<DataListener> listeners=new CopyOnWriteArrayList<>();
        public  void addListener(DataListener dataListener){
            this.listeners.add(dataListener);
        }
        public  void removeListener(DataListener dataListener){
           this.listeners.remove(dataListener);
        }
        public void notifyData(String content){
              for (DataListener listener:listeners){
                  listener.onChange(content);
              }
        }
        public void release(){
            listeners.clear();
        }
    }


}
