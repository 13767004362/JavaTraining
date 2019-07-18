package com.xingen.sixprinciples;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 开闭原则
 */
public class OpenClosePrinciples {


    public static void test() {
        // 构建一个数据操作者
        DataOperator operator = new DataOperator();
        operator.setQueryMonitor(new QueryMonitor() { // 自定义扩展，
                    @Override
                    public String monitor() {
                         try {
                             Thread.sleep(2*1000);
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                        return "开闭原则,抽象扩展实现：2秒后，监听到数据发生改变";
                    }
                })
                .query(new DataListener() {    // 添加监听器
                    @Override
                    public void onChange(String content) {
                        System.out.println(content);
                        //不再使用，释放资源
                        operator.release();
                    }
                });
    }

    /**
     * 定义一个数据变化回调器
     */
    public interface DataListener {
        void onChange(String content);
    }

    /**
     * 定义一个数据操作者
     */
    private static class DataOperator {
        // 监控数据的线程池
        private ExecutorService executor;
        private DataMonitor dataCache;
        private QueryMonitor queryMonitor;

        public DataOperator() {
            this.executor = Executors.newSingleThreadExecutor();
            this.dataCache = new DataMonitor();
        }

        public DataOperator setQueryMonitor(QueryMonitor queryMonitor) {
            this.queryMonitor = queryMonitor;
            return this;
        }


        public DataOperator query(DataListener listener) {
            dataCache.addListener(listener);
            if (this.queryMonitor == null) {
                this.queryMonitor = new DefaultQueryMonitor();
            }
            // 模拟异步线程，监控数据
            executor.execute(new DefaultQueryThread(dataCache, queryMonitor));
            return this;
        }

        public void release() {
            executor.shutdown();
            dataCache.release();
        }
    }

    /**
     * 抽象出一个数据变化查询接口
     */
    public interface QueryMonitor {
        String monitor();
    }

    /**
     * 默认的数据查询类
     */
    private static class DefaultQueryMonitor implements QueryMonitor {

        @Override
        public String monitor() {
            try {
                Thread.sleep(1000);
                return " 一秒后，监听到数据发生变化";
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * 定义一个专门监听数据变化的Runnable子类
     */
    public static class DefaultQueryThread implements Runnable {
        private DataMonitor dataMonitor;
        private QueryMonitor queryMonitor;

        public DefaultQueryThread(DataMonitor dataMonitor, QueryMonitor queryMonitor) {
            this.dataMonitor = dataMonitor;
            this.queryMonitor = queryMonitor;
        }

        @Override
        public void run() {
            try {
                String content = queryMonitor.monitor();
                dataMonitor.notifyData(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 定义一个类，专门缓存监听器,通知数据变化
     */
    private static class DataMonitor {
        private List<DataListener> listeners = new CopyOnWriteArrayList<>();

        public void addListener(DataListener dataListener) {
            this.listeners.add(dataListener);
        }

        public void removeListener(DataListener dataListener) {
            this.listeners.remove(dataListener);
        }

        public void notifyData(String content) {
            for (DataListener listener : listeners) {
                listener.onChange(content);
            }
        }

        public void release() {
            listeners.clear();
            listeners = null;
        }
    }


}
