package com.xingen.threaddemo.thradlocal;

/**
 * Created by ${新根} on 2018/12/23.
 * 博客：http://blog.csdn.net/hexingen
 */
public class DataBean {
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    public void increase(){
        i++;
    }
    public static DataBean newInstance(){
        return  new DataBean();
    }
}
