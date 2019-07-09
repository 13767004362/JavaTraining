package com.xingen.threaddemo.Volatile;

/**
 * Created by ${新根} on 2018/12/23.
 * 博客：http://blog.csdn.net/hexingen
 */
public class WorkThread extends Thread {
    private VolatileBean volatileBean;
    public WorkThread(VolatileBean volatileBean) {
        this.volatileBean = volatileBean;
    }

    @Override
    public void run() {
        super.run();

        while (!volatileBean.isStop()){

            try {
                this.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(WorkThread.class.getSimpleName()+" 执行任务 ");

        }
        System.out.println(WorkThread.class.getSimpleName()+" 获取到volatile变量的值 "+volatileBean.isStop());
    }
}
