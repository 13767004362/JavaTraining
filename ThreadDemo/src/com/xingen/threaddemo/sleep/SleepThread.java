package com.xingen.threaddemo.sleep;

public class SleepThread extends Thread {
    private static final String TAG=SleepThread.class.getSimpleName();
    @Override
    public void run() {
        try {
            long startTime=System.currentTimeMillis();
            System.out.println(TAG+" 开始沉睡，时间是 "+startTime);
            Thread.sleep(1000);
            long endTime=System.currentTimeMillis();
            System.out.println(TAG+" 结束沉睡，时间是 "+endTime+"  , 沉睡了"+(endTime-startTime)/1000+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  static SleepThread newInstance(){
        return new SleepThread();
    }
}
